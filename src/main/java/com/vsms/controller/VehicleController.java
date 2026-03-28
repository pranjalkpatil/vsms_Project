package com.vsms.controller;

import com.vsms.DTO.CustomerResponseDTO;
import com.vsms.DTO.VehicleRequestDTO;
import com.vsms.DTO.VehicleResponseDTO;
import com.vsms.entity.Vehicle;
import com.vsms.service.CustomerService;
import com.vsms.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/vehicle")
public class VehicleController {


    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private CustomerService customerService;


    @GetMapping("/viewAll")
    public String listVehicles(Model model, @RequestParam(value = "message", required = false) String message) {
        List<VehicleResponseDTO> vehicles = vehicleService.getAllVehicles();
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("message", message);
        return "vehicle/view-vehicles";
    }

    @GetMapping("/add")
    public String showAddVehicleForm(Model model) {
        List<CustomerResponseDTO> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        model.addAttribute("vehicle", new VehicleRequestDTO());
        return "vehicle/add-vehicle";
    }


    @PostMapping("/add")
    public String addVehicle(@ModelAttribute VehicleRequestDTO dto, RedirectAttributes rdr) {
        VehicleResponseDTO vehicle=vehicleService.addVehicle(dto);
        if(vehicle!=null) {
            return "redirect:/vehicle/viewAll?message=Vehicle added successfully!";
        }
        rdr.addFlashAttribute("vehicleControllerMessage", "Customer Not Found! Please Add Customer");
        return "redirect:/customer/add";
    }


    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable String id, Model model) {
        VehicleResponseDTO vehicle = vehicleService.getVehicleById(id);
        List<CustomerResponseDTO> customers = customerService.getAllCustomers();
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("customers", customers);
        return "vehicle/update-vehicle";
    }


    @PostMapping("/update")
    public String updateVehicle(@ModelAttribute VehicleRequestDTO dto, RedirectAttributes rdr) {
        vehicleService.updateVehicle(dto);
        rdr.addFlashAttribute("message", "Vehicle updated successfully!");
        return "redirect:/vehicle/viewAll";
    }



    @GetMapping("/delete/{id}")
    public String deleteVehicle(@PathVariable String id, RedirectAttributes rdr) {
        vehicleService.deleteVehicle(id);
        rdr.addFlashAttribute("message", "Vehicle deleted successfully!");
        return "redirect:/vehicle/viewAll";
    }


    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<VehicleResponseDTO>> getVehiclesByCustomer(@PathVariable String customerId) {
        return ResponseEntity.ok(vehicleService.getVehiclesByCustomerId(customerId));
    }

}
