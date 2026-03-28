package com.vsms.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class VehicleCreateDTO {

    private String id; // nullable — used to detect existing vs new
    private String registrationNumber;
    private String model;
    private String year;
    private String fuelType;
}
