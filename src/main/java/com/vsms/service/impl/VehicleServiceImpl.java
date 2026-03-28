package com.vsms.service.impl;

import com.vsms.DTO.VehicleRequestDTO;
import com.vsms.DTO.VehicleResponseDTO;
import com.vsms.entity.Customer;
import com.vsms.entity.Vehicle;
import com.vsms.repository.CustomerRepository;
import com.vsms.repository.VehicleRepository;
import com.vsms.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public VehicleResponseDTO addVehicle(VehicleRequestDTO dto) {
        if (vehicleRepository.existsByRegistrationNumber(dto.getRegistrationNumber())) {
            throw new IllegalArgumentException("Vehicle with this registration number already exists");
        }

        Customer customer = customerRepository.findById(dto.getCustomerId()).orElse(null);
        if (customer == null) return null;

        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber(dto.getRegistrationNumber());
        vehicle.setModel(dto.getModel());
        vehicle.setYear(dto.getYear());
        vehicle.setFuelType(dto.getFuelType());
        vehicle.setOwner(customer);

        return mapToResponse(vehicleRepository.save(vehicle));
    }


    @Override
    public VehicleResponseDTO getVehicleById(String id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        return mapToResponse(vehicle);
    }

    @Override
    public List<VehicleResponseDTO> getAllVehicles() {
        return vehicleRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleResponseDTO> getVehiclesByCustomerId(String customerId) {
        return vehicleRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleResponseDTO updateVehicle(VehicleRequestDTO dto) {
        Vehicle vehicle = vehicleRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        vehicle.setRegistrationNumber(dto.getRegistrationNumber());
        vehicle.setModel(dto.getModel());
        vehicle.setYear(dto.getYear());
        vehicle.setFuelType(dto.getFuelType());

        if (dto.getCustomerId() != null) {
            Customer customer = customerRepository.findById(dto.getCustomerId()).orElse(null);
            vehicle.setOwner(customer);
        }

        return mapToResponse(vehicleRepository.save(vehicle));
    }


    @Override
    public void deleteVehicle(String id) {
        vehicleRepository.deleteById(id);
    }

    private VehicleResponseDTO mapToResponse(Vehicle vehicle) {
        VehicleResponseDTO dto = new VehicleResponseDTO();

        dto.setId(vehicle.getId());  // <-- Add this line
        dto.setRegistrationNumber(vehicle.getRegistrationNumber());
        dto.setModel(vehicle.getModel());
        dto.setYear(vehicle.getYear());
        dto.setFuelType(vehicle.getFuelType());

        if (vehicle.getOwner() != null) {
            dto.setCustomerId(vehicle.getOwner().getId());
            dto.setCustomerName(vehicle.getOwner().getName());
        }

        return dto;
    }
}
