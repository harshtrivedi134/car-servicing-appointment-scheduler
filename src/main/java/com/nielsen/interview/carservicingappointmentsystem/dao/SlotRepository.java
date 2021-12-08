package com.nielsen.interview.carservicingappointmentsystem.dao;

import com.nielsen.interview.carservicingappointmentsystem.constants.ServiceType;
import com.nielsen.interview.carservicingappointmentsystem.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {

    @Query( value = "SELECT * FROM appointmentscheduler.public.slot s WHERE s.station_id IN (SELECT s.station_id FROM appointmentscheduler.public.slot s WHERE s.appointment_appointment_id IS NULL AND s.start_hours >= :startHours AND s.start_hours < :startHours + :duration AND s.start_date = :startDate GROUP BY s.station_id HAVING count(*) = :duration LIMIT 1)\n" +
            " AND s.appointment_appointment_id IS NULL\n" +
            " AND s.start_hours >= :startHours\n" +
            " AND s.start_hours < :startHours + :duration\n" +
            " AND s.start_date = :startDate",
    nativeQuery = true)
    List<Slot> viewAllAvailableSlotsForDate(@Param("startDate") LocalDate startDate,
                                                   @Param("startHours") int startHours, @Param("duration")
                                                   int duration);

}
