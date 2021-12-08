package com.nielsen.interview.carservicingappointmentsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "slot")
@Data
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slot_id")
    private Long slotId;

    @Column(name = "station_id")
    private Long stationId;

    @DateTimeFormat(pattern = "yyy-MM-dd")
    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "start_hours")
    private int startHours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Appointment appointment;

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Slot)) return false;
        Slot slot = (Slot) o;
        return this.getSlotId().equals(slot.getSlotId());
    }

    @Override
    public int hashCode(){
        return this.getSlotId().hashCode();
    }

    public Long getSlotId() {
        return slotId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
