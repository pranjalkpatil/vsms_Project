package com.vsms.service.impl;

import com.vsms.DTO.ServiceBookingResponseDTO;
import com.vsms.DTO.ServiceCenterRequestDTO;
import com.vsms.DTO.ServiceCenterResponseDTO;
import com.vsms.entity.ServiceCenter;
import com.vsms.repository.ServiceCenterRepository;
import com.vsms.service.ServiceCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ServiceCenterImpl implements ServiceCenterService {

    @Autowired
    private ServiceCenterRepository serviceCenterRepository;

    @Override
    public ServiceCenterResponseDTO create(ServiceCenterRequestDTO dto) {
        ServiceCenter center = new ServiceCenter();


        center.setName(dto.getName());
        center.setAddress(dto.getAddress());
        center.setContactNumber(dto.getContactNumber());
        center.setEmail(dto.getEmail());

        return mapToResponse(serviceCenterRepository.save(center));
    }

    @Override
    public List<ServiceCenterResponseDTO> getAll() {
        return serviceCenterRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceCenterResponseDTO getById(String id) {
        ServiceCenter center = serviceCenterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service Center not found"));
        return mapToResponse(center);
    }

    @Override
    public ServiceCenterResponseDTO update(String id, ServiceCenterRequestDTO dto) {
        ServiceCenter center = serviceCenterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service Center not found"));

        center.setName(dto.getName());
        center.setAddress(dto.getAddress());
        center.setContactNumber(dto.getContactNumber());
        center.setEmail(dto.getEmail());

        return mapToResponse(serviceCenterRepository.save(center));
    }

    @Override
    public void delete(String id) {
        serviceCenterRepository.deleteById(id);
    }

    private ServiceCenterResponseDTO mapToResponse(ServiceCenter center) {
        ServiceCenterResponseDTO dto = new ServiceCenterResponseDTO();

        dto.setId(center.getId());
        dto.setName(center.getName());
        dto.setAddress(center.getAddress());
        dto.setContactNumber(center.getContactNumber());
        dto.setEmail(center.getEmail());

        // ✅ Include service bookings in response
        if (center.getBookings() != null) {
            List<ServiceBookingResponseDTO> bookingDTOs = center.getBookings().stream().map(booking -> {
                ServiceBookingResponseDTO bookingDTO = new ServiceBookingResponseDTO();
                bookingDTO.setStatus(booking.getStatus());
                bookingDTO.setBookingDate(booking.getBookingDate());
                bookingDTO.setServiceDate(booking.getServiceDate());
                bookingDTO.setCustomerName(booking.getCustomer().getName());
                bookingDTO.setVehicleNumber(booking.getVehicle().getRegistrationNumber());
                return bookingDTO;
            }).collect(Collectors.toList());

            dto.setBookings(bookingDTOs);
        }
        return dto;
    }
}
