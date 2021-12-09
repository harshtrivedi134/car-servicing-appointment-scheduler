package com.nielsen.interview.carservicingappointmentsystem.service.impl;

import com.nielsen.interview.carservicingappointmentsystem.constants.AppointmentStatus;
import com.nielsen.interview.carservicingappointmentsystem.dao.AppointmentRepository;
import com.nielsen.interview.carservicingappointmentsystem.dao.CustomerRepository;
import com.nielsen.interview.carservicingappointmentsystem.dao.ServiceCatalogRepository;
import com.nielsen.interview.carservicingappointmentsystem.dao.SlotRepository;
import com.nielsen.interview.carservicingappointmentsystem.entity.Appointment;
import com.nielsen.interview.carservicingappointmentsystem.entity.Customer;
import com.nielsen.interview.carservicingappointmentsystem.entity.ServiceCatalog;
import com.nielsen.interview.carservicingappointmentsystem.entity.Slot;
import com.nielsen.interview.carservicingappointmentsystem.exception.AppointmentBookingException;
import com.nielsen.interview.carservicingappointmentsystem.exception.CustomerNotFoundException;
import com.nielsen.interview.carservicingappointmentsystem.model.dto.AppointmentRequest;
import com.nielsen.interview.carservicingappointmentsystem.service.AppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Service
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final CustomerRepository customerRepository;
    private final ServiceCatalogRepository serviceCatalogRepository;
    private final SlotRepository slotRepository;


    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                                  CustomerRepository customerRepository,
                                  ServiceCatalogRepository serviceCatalogRepository, SlotRepository slotRepository) {
        this.appointmentRepository = appointmentRepository;
        this.customerRepository = customerRepository;
        this.serviceCatalogRepository = serviceCatalogRepository;
        this.slotRepository = slotRepository;
    }

    @Override
    public Appointment createNewAppointment(AppointmentRequest appointmentRequest) {
        Customer customer = customerRepository.getById(appointmentRequest.getCustomerId());
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found for customerId" +
                    appointmentRequest.getCustomerId());
        }
        List<Slot> slotsToBook = slotRepository.findAllById(appointmentRequest.getSlotIds());
        validateSlots(slotsToBook);
        List<ServiceCatalog> serviceCatalogs = serviceCatalogRepository.findAllById(appointmentRequest.getServiceIds());
        Appointment appointment = createAppointment(customer, appointmentRequest.getDescription(), serviceCatalogs);
        slotsToBook.forEach(availableSlot -> availableSlot.setAppointment(appointment));
        log.info("updating slots");
        appointment.setSlots(slotsToBook);
        log.info("saving appointment");
        return appointmentRepository.save(appointment);
    }

    private Appointment createAppointment(Customer customer, String description, List<ServiceCatalog> serviceCatalogs) {
        Appointment appointment = new Appointment();
        appointment.setCustomer(customer);
        appointment.setDescription(description);
        appointment.setServiceCatalogs(serviceCatalogs);
        int totalCost = serviceCatalogs.stream().mapToInt(ServiceCatalog::getPrice).sum();
        appointment.setTotalPrice(totalCost);
        appointment.setAppointmentStatus(AppointmentStatus.BOOKED);
        appointment.setCreatedAt(LocalDateTime.now());
        return appointment;
    }


    private void validateSlots(List<Slot> slotsToBook) {
        int emptySlotsCount = (int) slotsToBook.stream().filter(Objects::nonNull).map(Slot::getAppointment)
                .filter(Objects::isNull).count();
        if (slotsToBook.size() != emptySlotsCount) {
            log.error("Appointment cannot be booked");
            throw new AppointmentBookingException("Slots are already booked ");
        }
    }


    @Override
    public Optional<Appointment> findAppointmentById(Long appointmentId) {
        return appointmentRepository.findAppointmentById(appointmentId);
    }

    @Override
    public Appointment updateAppointment(Appointment appointment, AppointmentStatus updatedStatus) {
        log.warn("Inside updateAppointment");
        if (appointment.getAppointmentStatus().equals(AppointmentStatus.CANCELLED) &&
                updatedStatus.equals(AppointmentStatus.CANCELLED)) {
            log.warn("Appointment {} is already cancelled", appointment.getAppointmentId());
            //Assuming we are only allowing change of status from booked to cancelled
            return appointment;
        }

        log.warn("Proceeding to make slots available for appointment {}", appointment.getAppointmentId());
        if (AppointmentStatus.CANCELLED.equals(updatedStatus)) {
            appointment.getSlots().forEach(slot -> {
                slot.setAppointment(null);
                slotRepository.save(slot);
            });
        }
        appointment.setAppointmentStatus(updatedStatus);
        appointment.setLastModifiedAt(LocalDateTime.now());
        return appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(Appointment appointment) {
        appointment.setAppointmentStatus(AppointmentStatus.DELETED);
        appointmentRepository.save(appointment);
    }

    @Override
    public Optional<Collection<Appointment>> retrieveAllAppointmentsBetweenDateRangeSortedByPrice(LocalDate beginDate,
                                                                                                  LocalDate endDate) {
        return appointmentRepository.getAllBookedAppointmentsBetweenDateRange(beginDate, endDate);
    }
}
