package com.vsms.controller;


import com.vsms.DTO.ServiceBookingRequestDTO;
import com.vsms.DTO.ServiceBookingResponseDTO;

import com.vsms.DTO.VehicleRequestDTO;
import com.vsms.DTO.VehicleResponseDTO;
import com.vsms.service.CustomerService;
import com.vsms.service.ServiceBookingService;
import com.vsms.service.ServiceCenterService;
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
@RequestMapping("/booking")
public class ServiceBookingController {

    @Autowired
    private ServiceBookingService bookingService;

    @Autowired
    private CustomerService customerService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ServiceCenterService centerService;

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("booking", new ServiceBookingRequestDTO());
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        model.addAttribute("centers", centerService.getAll());
        return "booking/add-booking";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute ServiceBookingRequestDTO dto, RedirectAttributes rd) {
        bookingService.createBooking(dto);
        rd.addFlashAttribute("message", "Booking created!");
        return "redirect:/booking/all";
    }

    @GetMapping("/all")
    public String viewAll(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "booking/view-bookings";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id, RedirectAttributes rd) {
        bookingService.deleteBooking(id);
        rd.addFlashAttribute("message", "Booking deleted!");
        return "redirect:/booking/all";
    }

    @PostMapping("/status/{id}")
    public String updateStatus(@PathVariable String id, @RequestParam String status, RedirectAttributes rd) {
        bookingService.updateBookingStatus(id, status);
        rd.addFlashAttribute("message", "Status updated!");
        return "redirect:/booking/all";
    }
}