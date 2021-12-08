package com.nielsen.interview.carservicingappointmentsystem.dao;

import com.nielsen.interview.carservicingappointmentsystem.constants.ServiceType;
import com.nielsen.interview.carservicingappointmentsystem.entity.ServiceCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCatalogRepository extends JpaRepository<ServiceCatalog, Long> {

    @Query("SELECT s.duration FROM ServiceCatalog s WHERE s.serviceType = :serviceType")
    int findDurationByServiceType(ServiceType serviceType);

    @Query("SELECT s.price FROM ServiceCatalog s WHERE s.serviceType = :serviceType")
    int findPriceByServiceType(ServiceType serviceType);
}
