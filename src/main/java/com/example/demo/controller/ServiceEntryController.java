package com.example.demo.controller;

import com.example.demo.model.ServiceEntry;
import com.example.demo.model.ServicePart;
import com.example.demo.service.ServiceEntryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/service-entries")
public class ServiceEntryController {

    private final ServiceEntryService serviceEntryService;

    // ✅ Constructor injection only
    public ServiceEntryController(ServiceEntryService serviceEntryService) {
        this.serviceEntryService = serviceEntryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceEntry createServiceEntry(
            @RequestParam Long vehicleId,
            @RequestParam Long garageId,
            @RequestParam LocalDate serviceDate,
            @RequestParam Integer odometerReading,
            @RequestBody(required = false) List<ServicePart> parts
    ) {
        return serviceEntryService.createServiceEntry(
                vehicleId,
                garageId,
                serviceDate,
                odometerReading,
                parts
        );
    }

    @GetMapping("/vehicle/{vehicleId}")
    public List<ServiceEntry> getEntriesForVehicle(@PathVariable Long vehicleId) {
        return serviceEntryService.getEntriesForVehicle(vehicleId);
    }

    @GetMapping("/vehicle/{vehicleId}/range")
    public List<ServiceEntry> getEntriesForVehicleInRange(
            @PathVariable Long vehicleId,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to
    ) {
        return serviceEntryService.getEntriesForVehicleInRange(vehicleId, from, to);
    }

    @GetMapping("/garage/{garageId}/odometer")
    public List<ServiceEntry> getEntriesForGarageAboveOdometer(
            @PathVariable Long garageId,
            @RequestParam Integer minOdometer
    ) {
        return serviceEntryService.getEntriesForGarageAboveOdometer(garageId, minOdometer);
    }
}
