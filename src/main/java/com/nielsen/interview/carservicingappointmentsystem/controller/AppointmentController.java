package com.nielsen.interview.carservicingappointmentsystem.controller;

import com.nielsen.interview.carservicingappointmentsystem.constants.AppointmentStatus;
import com.nielsen.interview.carservicingappointmentsystem.constants.ServiceType;
import com.nielsen.interview.carservicingappointmentsystem.entity.Appointment;
import com.nielsen.interview.carservicingappointmentsystem.entity.Slot;
import com.nielsen.interview.carservicingappointmentsystem.model.dto.AppointmentRequestDto;
import com.nielsen.interview.carservicingappointmentsystem.model.dto.UpdateAppointmentStatusDto;
import com.nielsen.interview.carservicingappointmentsystem.service.AppointmentService;
import com.nielsen.interview.carservicingappointmentsystem.service.ServiceCatalogService;
import com.nielsen.interview.carservicingappointmentsystem.service.SlotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/appointments")
@Validated
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final ServiceCatalogService serviceCatalogService;
    private final SlotService slotService;

    public AppointmentController(AppointmentService appointmentService,
                                 ServiceCatalogService serviceCatalogService,
                                 SlotService slotService) {
        this.appointmentService = appointmentService;
        this.serviceCatalogService = serviceCatalogService;
        this.slotService = slotService;
    }

    @GetMapping("/availableSlots")
    public Collection<Slot> viewSlotAvailability(@RequestParam(value = "date")
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                 @RequestParam(value = "service_type") ServiceType serviceType,
                                                 @RequestParam(value = "start_hour") int startHour) {

        int desiredDuration = serviceCatalogService.findDurationOfService(serviceType);
        return slotService.findAvailableSlots(startHour, date, desiredDuration);
    }


    @PostMapping(value = "/create",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
    public Appointment createNewAppointment(@Valid @RequestBody AppointmentRequestDto appointmentRequestDto) {

        return appointmentService.createNewAppointment(appointmentRequestDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable("id") Long appointmentId) {
        Optional<Appointment> retrievedAppointment = appointmentService.findAppointmentById(appointmentId);
        return retrievedAppointment.map(appointment -> new ResponseEntity<>(appointment, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));

    }

    /**
     * Update status from existing appointment. BOOKED -> CANCELLED
     * If status changed to CANCELLED then make update to slot table and make it available
     */
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointmentStatus(@Valid @RequestBody UpdateAppointmentStatusDto
                                                                           updateAppointmentStatusDto) {
        Optional<Appointment> retrievedAppointment = appointmentService
                .findAppointmentById(updateAppointmentStatusDto.getAppointmentId());

        if (retrievedAppointment.isPresent()) {
            log.warn("Status is {}", AppointmentStatus.valueOf(updateAppointmentStatusDto.getStatus()));
            Appointment updatedAppointment = appointmentService.updateAppointment(retrievedAppointment.get(),
                    AppointmentStatus.valueOf(updateAppointmentStatusDto.getStatus()));
            return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Delete appointment -> set appointment status = DELETED soft delete.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Appointment> deleteExistingAppointment(@PathVariable("id") Long appointmentId) {
        Optional<Appointment> retrievedAppointment = appointmentService.findAppointmentById(appointmentId);
        if (retrievedAppointment.isPresent()) {
            appointmentService.deleteAppointment(retrievedAppointment.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    /**
     *  Retrieve all appointments between a given date range sorted by price
     * @param beginDate
     * @param endDate
     * @return
     */
    @GetMapping("/booked/dates")
    public ResponseEntity<Collection<Appointment>> getBookedAppointmentsBetweenDateRangeSortedByPrice(
            @RequestParam(value = "beginDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate beginDate,
            @RequestParam(value = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        Optional<Collection<Appointment>> desiredAppointments = appointmentService
                .retrieveAllAppointmentsBetweenDateRangeSortedByPrice(beginDate, endDate);

        return desiredAppointments.map(appointments -> new ResponseEntity<>(appointments, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

}
