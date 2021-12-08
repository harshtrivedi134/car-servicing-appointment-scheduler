package com.nielsen.interview.carservicingappointmentsystem.entity;


import com.nielsen.interview.carservicingappointmentsystem.constants.ServiceType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "service")
@Data
public class ServiceCatalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long serviceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type")
    private ServiceType serviceType;

    @Column(name = "price")
    private int price;

    @Column(name = "duration")
    private int duration;

}
