package com.vsms.service;

import com.vsms.DTO.VehicleRequestDTO;
import com.vsms.DTO.VehicleResponseDTO;

import java.util.List;

public interface VehicleService {

    VehicleResponseDTO addVehicle(VehicleRequestDTO dto);
    VehicleResponseDTO getVehicleById(String id);
    List<VehicleResponseDTO> getAllVehicles();
    List<VehicleResponseDTO> getVehiclesByCustomerId(String customerId);
    VehicleResponseDTO updateVehicle(VehicleRequestDTO dto);
    void deleteVehicle(String id);
}
