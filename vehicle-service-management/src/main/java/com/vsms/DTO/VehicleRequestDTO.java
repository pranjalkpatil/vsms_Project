package com.vsms.DTO;

import lombok.Data;

@Data
public class VehicleRequestDTO {

    private String id; // <-- Add this line
    private String registrationNumber;
    private String model;
    private String year;
    private String fuelType;
    private String customerId;
    private String customerName;
}
