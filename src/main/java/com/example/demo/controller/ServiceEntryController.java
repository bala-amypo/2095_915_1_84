package com.example.demo.controller;

import com.example.demo.model.ServiceEntry;
import com.example.demo.service.ServiceEntryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service-entries")
@Tag(name = "Service Entries")
public class ServiceEntryController {

    private final ServiceEntryService serviceEntryService;

    public ServiceEntryController(ServiceEntryService serviceEntryService) {
        this.serviceEntryService = serviceEntryService;
    }

    @PostMapping
    public ServiceEntry create(@RequestBody ServiceEntry entry) {
        return serviceEntryService.createServiceEntry(entry);
    }

    @GetMapping("/{id}")
    public ServiceEntry getById(@PathVariable Long id) {
        return serviceEntryService.getServiceEntryById(id);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public List<ServiceEntry> getByVehicle(@PathVariable Long vehicleId) {
        return serviceEntryService.getEntriesForVehicle(vehicleId);
    }

    @GetMapping("/garage/{garageId}")
    public List<ServiceEntry> getByGarage(@PathVariable Long garageId) {
        return serviceEntryService.getEntriesByGarage(garageId);
    }
}
