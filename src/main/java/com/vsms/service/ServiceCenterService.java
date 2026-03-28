package com.vsms.service;

import com.vsms.DTO.ServiceCenterRequestDTO;
import com.vsms.DTO.ServiceCenterResponseDTO;

import java.util.List;

public interface ServiceCenterService {

    ServiceCenterResponseDTO create(ServiceCenterRequestDTO dto);
    List<ServiceCenterResponseDTO> getAll();
    ServiceCenterResponseDTO getById(String id);
    ServiceCenterResponseDTO update(String id, ServiceCenterRequestDTO dto);
    void delete(String id);


}
