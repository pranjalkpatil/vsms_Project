package com.vsms.DTO;

import lombok.Data;

import java.util.List;

@Data
public class CustomerResponseDTO {

    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private List<VehicleSummaryDTO> vehicles;
}
