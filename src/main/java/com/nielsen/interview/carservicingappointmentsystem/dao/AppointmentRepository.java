package com.nielsen.interview.carservicingappointmentsystem.dao;

import com.nielsen.interview.carservicingappointmentsystem.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("select a from Appointment a where a.customer.customerId = :customerId")
    List<Appointment> findAppointmentB(@Param("customerId") int customerId);

    @Query("select a from Appointment a where a.appointmentId = :appointmentId and a.appointmentStatus <> 'DELETED'")
    Optional<Appointment> findAppointmentById(@Param("appointmentId") Long appointmentId);

    @Query("SELECT a FROM Appointment a WHERE a.appointmentId IN (SELECT distinct s.appointment from Slot s WHERE s.startDate >= :beginDate AND s.startDate <= :endDate AND s.appointment IS NOT NULL) ORDER BY a.totalPrice DESC")
    Optional<Collection<Appointment>> getAllBookedAppointmentsBetweenDateRange(@Param("beginDate") LocalDate beginDate, @Param("endDate") LocalDate endDate);

}
