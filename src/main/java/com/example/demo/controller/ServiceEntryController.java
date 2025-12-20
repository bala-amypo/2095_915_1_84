package com.example.demo.controller;

import com.example.demo.model.ServiceEntry;
import com.example.demo.service.ServiceEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service-entries")
public class ServiceEntryController {

    @Autowired
    private ServiceEntryService serviceEntryService;

    @PostMapping
    public ServiceEntry createServiceEntry(@RequestBody ServiceEntry entry) {
        return serviceEntryService.createServiceEntry(entry);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public List<ServiceEntry> getEntriesForVehicle(@PathVariable Long vehicleId) {
        return serviceEntryService.getEntriesForVehicle(vehicleId);
    }
}
