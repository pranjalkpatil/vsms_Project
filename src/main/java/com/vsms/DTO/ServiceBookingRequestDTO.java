package com.vsms.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServiceBookingRequestDTO {

    private String customerId;
    private String vehicleId;
    private String serviceCenterId;
    private LocalDateTime serviceDate;
    private String status;
}
