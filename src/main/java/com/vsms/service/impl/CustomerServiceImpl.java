package com.vsms.service.impl;

import com.vsms.DTO.*;
import com.vsms.entity.Customer;
import com.vsms.entity.Vehicle;
import com.vsms.repository.CustomerRepository;
import com.vsms.service.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto) {
        Customer customer=new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());

        List<Vehicle>vehicleList=new ArrayList<>();

        if(dto.getVehicles()!=null){
            for(VehicleCreateDTO vdto: dto.getVehicles()){
                Vehicle vehicle = new Vehicle();
                vehicle.setRegistrationNumber(vdto.getRegistrationNumber());
                vehicle.setModel(vdto.getModel());
                vehicle.setYear(vdto.getYear());
                vehicle.setFuelType(vdto.getFuelType());
                vehicle.setOwner(customer); // 🔗 set bidirectional relationship
                vehicleList.add(vehicle);
            }
        }
        customer.setVehicles(vehicleList);
        Customer savedCustomer=customerRepository.save(customer);
        return mapToResponse(savedCustomer);
    }

    @Override
    public CustomerResponseDTO getCustomerById(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return mapToResponse(customer);
    }

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CustomerResponseDTO updateCustomer(String id, CustomerRequestDTO dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Step 1: Update basic info
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());

        // Step 2: Vehicle handling
        List<Vehicle> existingVehicles = customer.getVehicles(); // already linked
        List<Vehicle> updatedVehicles = new ArrayList<>();

        List<String> incomingVehicleIds = new ArrayList<>();

        if (dto.getVehicles() != null) {
            for (VehicleCreateDTO vdto : dto.getVehicles()) {
                if (vdto.getId() != null) {
                    // Updating existing vehicle
                    Vehicle existing = existingVehicles.stream()
                            .filter(v -> v.getId().equals(vdto.getId()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Vehicle ID not found: " + vdto.getId()));

                    existing.setRegistrationNumber(vdto.getRegistrationNumber());
                    existing.setModel(vdto.getModel());
                    existing.setYear(vdto.getYear());
                    existing.setFuelType(vdto.getFuelType());

                    updatedVehicles.add(existing);
                    incomingVehicleIds.add(existing.getId());
                } else {
                    // New vehicle
                    Vehicle newVehicle = new Vehicle();
                    newVehicle.setRegistrationNumber(vdto.getRegistrationNumber());
                    newVehicle.setModel(vdto.getModel());
                    newVehicle.setYear(vdto.getYear());
                    newVehicle.setFuelType(vdto.getFuelType());
                    newVehicle.setOwner(customer);
                    updatedVehicles.add(newVehicle);
                }
            }
        }

        // Step 3: Remove vehicles not in incoming list
        List<Vehicle> toRemove = existingVehicles.stream()
                .filter(v -> !incomingVehicleIds.contains(v.getId()))
                .toList();

        existingVehicles.removeAll(toRemove);         // remove old
        existingVehicles.clear();                     // clear for re-adding
        existingVehicles.addAll(updatedVehicles);     // add updated + new

        customer.setVehicles(existingVehicles);

        Customer updatedCustomer = customerRepository.save(customer);
        return mapToResponse(updatedCustomer);
    }

    @Override
    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }

    private CustomerResponseDTO mapToResponse(Customer customer) {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setAddress(customer.getAddress());

        if (customer.getVehicles() != null) {
            List<VehicleSummaryDTO> vehicleDTOs = customer.getVehicles().stream()
                    .map(vehicle -> {
                        VehicleSummaryDTO vdto = new VehicleSummaryDTO();
                        vdto.setRegistrationNumber(vehicle.getRegistrationNumber());
                        vdto.setModel(vehicle.getModel());
                        vdto.setYear(vehicle.getYear());
                        vdto.setFuelType(vehicle.getFuelType());
                        return vdto;
                    }).collect(Collectors.toList());
            dto.setVehicles(vehicleDTOs);
        }

        return dto;
    }
}
