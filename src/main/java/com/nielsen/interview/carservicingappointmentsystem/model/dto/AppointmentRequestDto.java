package com.nielsen.interview.carservicingappointmentsystem.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class AppointmentRequestDto {
    private String description;
    @NotNull
    private Long customerId;
    @NotEmpty
    private List<Long> serviceIds;
    @NotEmpty
    private List<Long> slotIds;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<Long> serviceIds) {
        this.serviceIds = serviceIds;
    }

    public List<Long> getSlotIds() {
        return slotIds;
    }

    public void setSlotIds(List<Long> slotIds) {
        this.slotIds = slotIds;
    }
}
