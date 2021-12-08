package com.nielsen.interview.carservicingappointmentsystem.service;
import com.nielsen.interview.carservicingappointmentsystem.entity.Slot;
import java.time.LocalDate;
import java.util.Collection;

public interface SlotService {
    Collection<Slot> findAvailableSlots(int startHours, LocalDate startDate, int duration);

}
