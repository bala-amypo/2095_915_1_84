package com.example.demo.controller;

import com.example.demo.model.ServiceEntry;
import com.example.demo.service.ServiceEntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service-entries")
public class ServiceEntryController {
    private final ServiceEntryService serviceEntryService;

    public ServiceEntryController(ServiceEntryService serviceEntryService) {
        this.serviceEntryService = serviceEntryService;
    }

    @PostMapping
    public ResponseEntity<ServiceEntry> createServiceEntry(@RequestBody ServiceEntry entry) {
        return ResponseEntity.ok(serviceEntryService.createServiceEntry(entry));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceEntry> updateServiceEntry(@PathVariable Long id, @RequestBody ServiceEntry entry) {
        return ResponseEntity.ok(serviceEntryService.updateServiceEntry(id, entry));
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<ServiceEntry>> getEntriesForVehicle(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(serviceEntryService.getEntriesForVehicle(vehicleId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceEntry> getServiceEntry(@PathVariable Long id) {
        return serviceEntryService.getServiceEntryById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ServiceEntry>> getAllServiceEntries() {
        return ResponseEntity.ok(serviceEntryService.getAllServiceEntries());
    }
}