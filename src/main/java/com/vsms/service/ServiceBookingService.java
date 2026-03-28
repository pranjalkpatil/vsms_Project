package com.vsms.service;

import com.vsms.DTO.ServiceBookingRequestDTO;
import com.vsms.DTO.ServiceBookingResponseDTO;

import java.util.List;

public interface ServiceBookingService {

    ServiceBookingResponseDTO createBooking(ServiceBookingRequestDTO dto);
    ServiceBookingResponseDTO getBookingById(String id);
    List<ServiceBookingResponseDTO> getAllBookings();
    ServiceBookingResponseDTO updateBookingStatus(String id, String status);
    void deleteBooking(String id);
}
