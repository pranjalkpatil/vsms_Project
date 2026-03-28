package com.vsms.DTO;

import lombok.Data;

import java.util.List;

@Data
public class CustomerRequestDTO {

    String name;
    String email;
    String phone;
    String address;
    private List<VehicleCreateDTO> vehicles;
}
