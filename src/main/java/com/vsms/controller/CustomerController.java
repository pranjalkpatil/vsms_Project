package com.vsms.controller;


import com.vsms.DTO.CustomerRequestDTO;
import com.vsms.DTO.CustomerResponseDTO;
import com.vsms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.util.JpaMetamodel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;



    //View All Customer in a Table
    @GetMapping("/viewAll")
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customer/view-customers";
    }

    //It Shows Add Customer Form by hiting the URL
    @GetMapping("/add")
    public String createCustomerForm(){

        return "customer/add-customer";
    }

    //it adds the Customer in database and Redirect into View-Customer --- Thymeleaf Validation Remaining
    @PostMapping("/add")
    public String createCustomer(@ModelAttribute CustomerRequestDTO dto, RedirectAttributes redirectAttributes) {

        customerService.createCustomer(dto);
        redirectAttributes.addFlashAttribute("message", "Customer added successfully!");
        return "redirect:/customer/viewAll";
    }

    //Get Specific Customer and use in JavaScript on CustomerModal.js
    @GetMapping("/get/{id}")
    @ResponseBody
    public CustomerResponseDTO getCustomer(@PathVariable String id) {

        CustomerResponseDTO customerResponseDTO = customerService.getCustomerById(id);
        return  customerResponseDTO;
    }


    //Get Update Customer Form
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable String id, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        return "customer/update-customer";
    }

    //Add Updated Customer into database and updated into view Customer page
    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable String id,@ModelAttribute CustomerRequestDTO dto, RedirectAttributes atr) {
        customerService.updateCustomer(id, dto);
        atr.addFlashAttribute("message","Customer Updated Successfully");
        return "redirect:/customer/viewAll";
    }

    // Note: Use @GetMapping for delete if you're calling from a link/button without JS/AJAX.
    //@DeleteMapping("/delete/{id}")
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable String id, RedirectAttributes redirectAttributes) {
        customerService.deleteCustomer(id);
        System.out.println("Customer Deleted....");
        redirectAttributes.addFlashAttribute("message","Customer Deleted Successfully");
        return "redirect:/customer/viewAll";
    }
}


