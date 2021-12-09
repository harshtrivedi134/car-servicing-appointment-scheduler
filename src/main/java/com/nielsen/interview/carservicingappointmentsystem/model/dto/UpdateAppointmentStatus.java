package com.nielsen.interview.carservicingappointmentsystem.model.dto;

import com.nielsen.interview.carservicingappointmentsystem.validation.ValuesAllowed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateAppointmentStatus {
    @NotNull
    private Long appointmentId;

    @NotBlank
    @ValuesAllowed(values = {"CANCELLED"}, propName = "status")
    private String status;

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
