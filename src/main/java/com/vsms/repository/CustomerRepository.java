package com.vsms.repository;

import com.vsms.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    public Customer findByName(String name);
}
