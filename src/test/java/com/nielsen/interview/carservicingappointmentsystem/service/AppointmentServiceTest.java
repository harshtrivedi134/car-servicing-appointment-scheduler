package com.nielsen.interview.carservicingappointmentsystem.service;

import com.nielsen.interview.carservicingappointmentsystem.constants.AppointmentStatus;
import com.nielsen.interview.carservicingappointmentsystem.constants.ServiceType;
import com.nielsen.interview.carservicingappointmentsystem.constants.TestConstants;
import com.nielsen.interview.carservicingappointmentsystem.dao.AppointmentRepository;
import com.nielsen.interview.carservicingappointmentsystem.dao.CustomerRepository;
import com.nielsen.interview.carservicingappointmentsystem.dao.ServiceCatalogRepository;
import com.nielsen.interview.carservicingappointmentsystem.dao.SlotRepository;
import com.nielsen.interview.carservicingappointmentsystem.entity.Appointment;
import com.nielsen.interview.carservicingappointmentsystem.entity.Customer;
import com.nielsen.interview.carservicingappointmentsystem.entity.ServiceCatalog;
import com.nielsen.interview.carservicingappointmentsystem.entity.Slot;
import com.nielsen.interview.carservicingappointmentsystem.model.dto.AppointmentRequest;
import com.nielsen.interview.carservicingappointmentsystem.service.impl.AppointmentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class AppointmentServiceTest {
    @InjectMocks
    AppointmentServiceImpl appointmentService;

    @Mock
    AppointmentRepository appointmentRepository;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    ServiceCatalogRepository serviceCatalogRepository;

    @Mock
    SlotRepository slotRepository;

    private Long appointmentId;
    private Appointment appointment;
    private Customer customer;
    private Collection<ServiceCatalog> serviceCatalogs;
    private String description;
    private AppointmentStatus appointmentStatus;
    private int totalPrice;
    private Collection<Slot> slots;
    private Optional<Appointment> optionalAppointment;
    private ServiceCatalog serviceCatalog;
    private Slot slot;


    @Before
    public void intit() {
        appointment = new Appointment();
        appointment.setAppointmentId(1L);
        // customer object
        customer = new Customer();
        customer.setCustomerId(1L);
        customer.setFirstName(TestConstants.CUSTOMER_NAME);
        customer.setVehicleModel(TestConstants.VEHICLE_MODEL);
        customer.setVin(TestConstants.VIN);
        appointment.setCustomer(customer);
        appointment.setTotalPrice(700);
        //serviceCatalogs
        serviceCatalogs = new ArrayList<>();
        serviceCatalog = new ServiceCatalog();
        serviceCatalog.setServiceId(1L);
        serviceCatalog.setServiceType(ServiceType.STANDARD);
        serviceCatalog.setPrice(700);
        serviceCatalog.setDuration(1);
        serviceCatalogs.add(serviceCatalog);
        appointment.setServiceCatalogs(serviceCatalogs);

        //create free slot
        slot = new Slot();
        slot.setSlotId(1L);
        slot.setStartHours(14);
        slot.setStationId(1L);
        slot.setStartDate(LocalDate.of(2021, 12, 6));

        optionalAppointment = Optional.of(appointment);
    }

    @Test
    public void shouldFindAppointmentById() {
        when(appointmentRepository.findAppointmentById(1L)).thenReturn(optionalAppointment);
        assertEquals(optionalAppointment.get().getAppointmentId(),
                appointmentService.findAppointmentById(1L).get().getAppointmentId());
        verify(appointmentRepository, times(1)).findAppointmentById(1L);
    }

    @Test
    public void bookAppointmentWhenSlotIsAvailable() {


        when(customerRepository.getById(1L)).thenReturn(customer);
        when(slotRepository.findAllById(new ArrayList<>(Arrays.asList(1L))))
                .thenReturn(new ArrayList<>(Arrays.asList(slot)));
        when(serviceCatalogRepository.findAllById(new ArrayList<>(Arrays.asList(1L))))
                .thenReturn(new ArrayList<>(Arrays.asList(serviceCatalog)));

        ArgumentCaptor<Appointment> argumentCaptor = ArgumentCaptor.forClass(Appointment.class);
        appointmentService.createNewAppointment(generateRequestDto());
        verify(appointmentRepository, times(1)).save(argumentCaptor.capture());

    }

    @Test(expected = RuntimeException.class)
    public void rejectAppointmentWhenSlotIsOccupied() {
        //pre-occupy the slot before request is sent
        slot.setAppointment(appointment);

        when(customerRepository.getById(1L)).thenReturn(customer);
        when(slotRepository.findAllById(new ArrayList<>(Arrays.asList(1L))))
                .thenReturn(new ArrayList<>(Arrays.asList(slot)));


        ArgumentCaptor<Appointment> argumentCaptor = ArgumentCaptor.forClass(Appointment.class);
        appointmentService.createNewAppointment(generateRequestDto());
        verify(appointmentRepository, times(1)).save(argumentCaptor.capture());
    }

    @Test
    public void updateStatusOfBookedAppointment() {
        slot.setAppointment(appointment);
        appointment.setSlots(new ArrayList<>(Arrays.asList(slot)));
        appointment.setAppointmentStatus(AppointmentStatus.BOOKED);

        Appointment updatedAppointment = new Appointment();
        updatedAppointment.setAppointmentId(1L);
        updatedAppointment.setAppointmentStatus(AppointmentStatus.CANCELLED);


        appointmentService.updateAppointment(appointment, AppointmentStatus.CANCELLED);
        verify(appointmentRepository).save(updatedAppointment);
    }


    private AppointmentRequest generateRequestDto() {
        AppointmentRequest appointmentRequest = new AppointmentRequest();
        appointmentRequest.setCustomerId(1L);
        appointmentRequest.setDescription(TestConstants.APPOINTMENT_DESCRIPTION);
        appointmentRequest.setSlotIds(new ArrayList<>(Arrays.asList(1L)));
        appointmentRequest.setServiceIds(new ArrayList<>(Arrays.asList(1L)));

        return appointmentRequest;
    }
}
