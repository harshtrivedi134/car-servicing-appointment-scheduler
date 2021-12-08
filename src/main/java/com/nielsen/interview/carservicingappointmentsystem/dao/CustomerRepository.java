package com.nielsen.interview.carservicingappointmentsystem.dao;

import com.nielsen.interview.carservicingappointmentsystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

}
