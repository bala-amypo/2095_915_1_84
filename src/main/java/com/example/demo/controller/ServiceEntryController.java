package com.example.demo.controller;

import com.example.demo.model.ServiceEntry;
import com.example.demo.service.ServiceEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service-entries")
@RequiredArgsConstructor
public class ServiceEntryController {

    private final ServiceEntryService serviceEntryService;

    // ✅ Create Service Entry
    @PostMapping
    public ResponseEntity<ServiceEntry> createServiceEntry(
            @RequestBody ServiceEntry serviceEntry) {

        ServiceEntry created = serviceEntryService.createServiceEntry(serviceEntry);
        return ResponseEntity.ok(created);
    }

    // ✅ Get Service Entry by ID
    @GetMapping("/{id}")
    public ResponseEntity<ServiceEntry> getServiceEntryById(@PathVariable Long id) {

        ServiceEntry entry = serviceEntryService.getServiceEntryById(id);
        return ResponseEntity.ok(entry);
    }

    // ✅ Get all service entries for a vehicle
    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<ServiceEntry>> getEntriesByVehicle(
            @PathVariable Long vehicleId) {

        List<ServiceEntry> entries =
                serviceEntryService.getServiceEntriesByVehicle(vehicleId);

        return ResponseEntity.ok(entries);
    }

    // ✅ Update Service Entry
    @PutMapping("/{id}")
    public ResponseEntity<ServiceEntry> updateServiceEntry(
            @PathVariable Long id,
            @RequestBody ServiceEntry serviceEntry) {

        ServiceEntry updated =
                serviceEntryService.updateServiceEntry(id, serviceEntry);

        return ResponseEntity.ok(updated);
    }

    // ✅ Delete Service Entry
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceEntry(@PathVariable Long id) {

        serviceEntryService.deleteServiceEntry(id);
        return ResponseEntity.noContent().build();
    }
}
