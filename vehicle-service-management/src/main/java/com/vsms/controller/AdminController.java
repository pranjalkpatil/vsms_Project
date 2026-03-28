package com.vsms.controller;

import com.vsms.service.*;
import com.vsms.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ServiceCenterService serviceCenterService;

    @Autowired
    private ServiceBookingService serviceBookingService;

    @GetMapping("/")
    public String showDashboard(Model model) {

        // Summary counts
        model.addAttribute("totalCustomers", customerService.getAllCustomers().size());
        model.addAttribute("totalVehicles", vehicleService.getAllVehicles().size());
        model.addAttribute("totalBookings", serviceBookingService.getAllBookings().size());
        model.addAttribute("totalCenters", serviceCenterService.getAll().size());

        // Data lists for tables
        List<CustomerResponseDTO> customers = customerService.getAllCustomers();
        List<VehicleResponseDTO> vehicles = vehicleService.getAllVehicles();
        List<ServiceCenterResponseDTO> centers = serviceCenterService.getAll();
        List<ServiceBookingResponseDTO> bookings = serviceBookingService.getAllBookings();

        model.addAttribute("customers", customers);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("centers", centers);
        model.addAttribute("bookings", bookings);

        return "admin/dashboard";
    }

    // ✅ View Customer
    @GetMapping("/view-customer/{id}")
    public String viewCustomer(@PathVariable String id, Model model) {
        CustomerResponseDTO customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "admin/view-customer";
    }

    @GetMapping("/view-vehicle/{id}")
    public String viewVehicle(@PathVariable String id, Model model) {
        VehicleResponseDTO vehilce = vehicleService.getVehicleById(id);
        model.addAttribute("vehicle", vehilce);
        return "admin/view-vehicle";
    }


    // ✅ View Service Center
    @GetMapping("/view-center/{id}")
    public String viewCenter(@PathVariable String id, Model model) {
        ServiceCenterResponseDTO center = serviceCenterService.getById(id);
        model.addAttribute("center", center);
        return "admin/view-center";
    }

    // ✅ View Booking
    @GetMapping("/view-booking/{id}")
    public String viewBooking(@PathVariable String id, Model model) {
        ServiceBookingResponseDTO booking = serviceBookingService.getBookingById(id);
        model.addAttribute("booking", booking);
        return "admin/view-booking";
    }
}
