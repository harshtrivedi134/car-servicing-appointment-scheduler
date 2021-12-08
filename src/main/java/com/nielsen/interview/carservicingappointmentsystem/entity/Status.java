package com.nielsen.interview.carservicingappointmentsystem.entity;

import com.nielsen.interview.carservicingappointmentsystem.constants.AppointmentStatus;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "status")
@Data
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Long statusId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_name")
    private AppointmentStatus appointmentStatus;

    @Column(name = "description")
    private String description;

}
