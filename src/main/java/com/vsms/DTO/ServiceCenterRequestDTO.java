package com.vsms.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServiceCenterRequestDTO {

    String id;
    String name;
    String address;
    String contactNumber;
    String email;
}
