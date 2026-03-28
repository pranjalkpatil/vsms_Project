package com.vsms.service;

import com.vsms.DTO.CustomerRequestDTO;
import com.vsms.DTO.CustomerResponseDTO;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    CustomerResponseDTO createCustomer(CustomerRequestDTO dto);
    CustomerResponseDTO getCustomerById(String id);
    List<CustomerResponseDTO> getAllCustomers();
    CustomerResponseDTO updateCustomer(String id, CustomerRequestDTO dto);
    void deleteCustomer(String id);
//    CustomerResponseDTO getById(String id);

}
