package com.example.demo.controller;

import com.example.demo.model.ServicePart;
import com.example.demo.service.ServicePartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service-parts")
@Tag(name = "Service Parts")
public class ServicePartController {

    private final ServicePartService servicePartService;

    public ServicePartController(ServicePartService servicePartService) {
        this.servicePartService = servicePartService;
    }

    @PostMapping
    public ServicePart create(@RequestBody ServicePart part) {
        return servicePartService.createPart(part);
    }

    @GetMapping("/{id}")
    public ServicePart getById(@PathVariable Long id) {
        return servicePartService.getPartById(id);
    }

    @GetMapping("/entry/{entryId}")
    public List<ServicePart> getByEntry(@PathVariable Long entryId) {
        return servicePartService.getPartsForEntry(entryId);
    }
}
