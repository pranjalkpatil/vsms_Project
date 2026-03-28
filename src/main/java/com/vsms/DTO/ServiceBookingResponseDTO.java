package com.vsms.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServiceBookingResponseDTO {


    private String id;
    private LocalDateTime bookingDate;
    private LocalDateTime serviceDate;
    private String status;

    private String customerName;
    private String vehicleNumber;
    private String serviceCenterName;
}
