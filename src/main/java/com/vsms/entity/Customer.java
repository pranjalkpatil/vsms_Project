package com.vsms.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    String email;
    String phone;
    String address;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehicle> vehicles = new ArrayList<>();
}
