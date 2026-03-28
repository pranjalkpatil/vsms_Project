package com.vsms.repository;

import com.vsms.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    boolean existsByRegistrationNumber(String registrationNumber);

}
