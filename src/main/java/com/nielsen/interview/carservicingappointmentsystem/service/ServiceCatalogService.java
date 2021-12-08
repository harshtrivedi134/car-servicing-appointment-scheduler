package com.nielsen.interview.carservicingappointmentsystem.service;

import com.nielsen.interview.carservicingappointmentsystem.constants.ServiceType;
import org.springframework.stereotype.Repository;

public interface ServiceCatalogService {
    int findDurationOfService(ServiceType serviceType);
}
