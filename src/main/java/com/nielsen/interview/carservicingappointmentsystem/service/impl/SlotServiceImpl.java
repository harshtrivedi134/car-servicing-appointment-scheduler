package com.nielsen.interview.carservicingappointmentsystem.service.impl;

import com.nielsen.interview.carservicingappointmentsystem.dao.SlotRepository;
import com.nielsen.interview.carservicingappointmentsystem.entity.Slot;
import com.nielsen.interview.carservicingappointmentsystem.service.SlotService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class SlotServiceImpl implements SlotService {
    private final SlotRepository slotRepository;

    public SlotServiceImpl(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    @Override
    public List<Slot> findAvailableSlots(int startHours, LocalDate startDate, int duration) {
        return slotRepository.viewAllAvailableSlotsForDate(startDate, startHours, duration);
    }
}
