package com.nielsen.interview.carservicingappointmentsystem.service;

import com.nielsen.interview.carservicingappointmentsystem.constants.AppointmentStatus;
import com.nielsen.interview.carservicingappointmentsystem.entity.Appointment;
import com.nielsen.interview.carservicingappointmentsystem.model.dto.AppointmentRequestDto;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Appointment createNewAppointment(AppointmentRequestDto appointmentRequestDto);

    Optional<Appointment> findAppointmentById(Long appointmentId);

    Appointment updateAppointment(Appointment appointment, AppointmentStatus updatedStatus);

    void deleteAppointment(Appointment appointment);

    Optional<Collection<Appointment>> retrieveAllAppointmentsBetweenDateRangeSortedByPrice(LocalDate startDate, LocalDate endDate);



}
