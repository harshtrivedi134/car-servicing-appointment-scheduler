package com.nielsen.interview.carservicingappointmentsystem.service.impl;

import com.nielsen.interview.carservicingappointmentsystem.constants.ServiceType;
import com.nielsen.interview.carservicingappointmentsystem.dao.ServiceCatalogRepository;
import com.nielsen.interview.carservicingappointmentsystem.service.ServiceCatalogService;
import org.springframework.stereotype.Service;

@Service
public class ServiceCatalogImpl implements ServiceCatalogService {
    private final ServiceCatalogRepository serviceCatalogRepository;

    public ServiceCatalogImpl(ServiceCatalogRepository serviceCatalogRepository) {
        this.serviceCatalogRepository = serviceCatalogRepository;
    }

    @Override
    public int findDurationOfService(ServiceType serviceType) {
        return serviceCatalogRepository.findDurationByServiceType(serviceType);
    }
}
