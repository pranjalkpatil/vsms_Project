package com.vsms.controller;


import com.vsms.DTO.ServiceCenterRequestDTO;
import com.vsms.DTO.ServiceCenterResponseDTO;
import com.vsms.service.ServiceCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/service-center")
public class ServiceCenterController {

    @Autowired
    private ServiceCenterService service;


    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("center", new ServiceCenterRequestDTO());
        return "service/add-service";
    }

    @PostMapping("/add")
    public String addCenter(@ModelAttribute("center") ServiceCenterRequestDTO dto,
                            RedirectAttributes redirectAttributes) {
        service.create(dto);
        redirectAttributes.addFlashAttribute("message", "Service Center added successfully!");
        return "redirect:/service-center/viewAll";
    }

    @GetMapping("/viewAll")
    public String viewAllCenters(Model model,
                                 @ModelAttribute("message") String message) {
        model.addAttribute("centers", service.getAll());
        model.addAttribute("message", message);
        return "service/view-services";
    }

    @GetMapping("/edit/{id}")
    public String editCenter(@PathVariable String id, Model model) {
        model.addAttribute("center", service.getById(id));
        model.addAttribute("id", id);
        return "service/update-service";
    }

    @PostMapping("/update/{id}")
    public String updateCenter(@PathVariable String id,
                               @ModelAttribute ServiceCenterRequestDTO dto,
                               RedirectAttributes redirectAttributes) {
        service.update(id, dto);
        redirectAttributes.addFlashAttribute("message", "Service Center updated successfully!");
        return "redirect:/service-center/viewAll";
    }

    @GetMapping("/delete/{id}")
    public String deleteCenter(@PathVariable String id,
                               RedirectAttributes redirectAttributes) {
        service.delete(id);
        redirectAttributes.addFlashAttribute("message", "Service Center deleted successfully!");
        return "redirect:/service-center/viewAll";
    }
}
