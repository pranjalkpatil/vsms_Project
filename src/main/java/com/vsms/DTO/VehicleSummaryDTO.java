package com.vsms.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class VehicleSummaryDTO {


    private String id;
    private String registrationNumber;
    private String model;
    private String year;
    private String fuelType;
}
