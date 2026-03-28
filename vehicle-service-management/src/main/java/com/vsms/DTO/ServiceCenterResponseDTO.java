package com.vsms.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ServiceCenterResponseDTO {

    private String id;
    private String name;
    private String address;
    private String contactNumber;
    private String email;
    private List<ServiceBookingResponseDTO> bookings;
}
