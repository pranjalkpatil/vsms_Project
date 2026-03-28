package com.vsms.service.impl;

import com.vsms.DTO.ServiceBookingRequestDTO;
import com.vsms.DTO.ServiceBookingResponseDTO;
import com.vsms.entity.Customer;
import com.vsms.entity.ServiceCenter;
import com.vsms.entity.Vehicle;
import com.vsms.repository.CustomerRepository;
import com.vsms.repository.ServiceBookingRepository;
import com.vsms.repository.ServiceCenterRepository;
import com.vsms.repository.VehicleRepository;
import com.vsms.entity.ServiceBooking;
import com.vsms.service.ServiceBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ServiceBookingImpl implements ServiceBookingService {

    @Autowired
    private ServiceBookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ServiceCenterRepository serviceCenterRepository;

    @Override
    public ServiceBookingResponseDTO createBooking(ServiceBookingRequestDTO dto) {
        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        ServiceCenter center = serviceCenterRepository.findById(dto.getServiceCenterId())
                .orElseThrow(() -> new RuntimeException("Service center not found"));

        ServiceBooking booking = new ServiceBooking();
        booking.setVehicle(vehicle);
        booking.setCustomer(customer);
        booking.setServiceCenter(center);
        booking.setBookingDate(LocalDateTime.now());
        booking.setServiceDate(dto.getServiceDate());
        booking.setStatus("Booked");

        return mapToResponse(bookingRepository.save(booking));
    }

    @Override
    public ServiceBookingResponseDTO getBookingById(String id) {
       ServiceBooking serviceBooking=bookingRepository.findById(id)
               .orElseThrow(()-> new RuntimeException("Service Booking Not Found"));

       return mapToResponse(serviceBooking);
    }

    @Override
    public List<ServiceBookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll().
                stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceBookingResponseDTO updateBookingStatus(String id, String status) {
        ServiceBooking serviceBooking=bookingRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Service Booking Not Found"));

        serviceBooking.setStatus(status);
        return mapToResponse(bookingRepository.save(serviceBooking));
    }

    @Override
    public void deleteBooking(String id) {
        bookingRepository.deleteById(id);
    }


    private ServiceBookingResponseDTO mapToResponse(ServiceBooking booking) {
        ServiceBookingResponseDTO dto = new ServiceBookingResponseDTO();

        dto.setId(booking.getId());
        dto.setBookingDate(booking.getBookingDate());
        dto.setServiceDate(booking.getServiceDate());
        dto.setStatus(booking.getStatus());

        dto.setCustomerName(booking.getCustomer().getName());
        dto.setVehicleNumber(booking.getVehicle().getRegistrationNumber());
        dto.setServiceCenterName(booking.getServiceCenter().getName());

        return dto;
    }
}
