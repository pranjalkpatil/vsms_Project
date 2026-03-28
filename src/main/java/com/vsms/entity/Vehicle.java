package com.vsms.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String registrationNumber;
    String model;          // e.g., Civic
    String year;           // e.g., 2020
    String fuelType;       // Petrol/Diesel/Electric


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer owner;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceBooking> bookings = new ArrayList<>();
}
