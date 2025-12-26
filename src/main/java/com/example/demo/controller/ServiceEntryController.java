package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.model.ServiceEntry;
import com.example.demo.model.ServicePart;
import com.example.demo.service.ServiceEntryService;

@RestController
@RequestMapping("/service-entries")
public class ServiceEntryController {

    private final ServiceEntryService serviceEntryService;

    public ServiceEntryController(ServiceEntryService serviceEntryService) {
        this.serviceEntryService = serviceEntryService;
    }

    // CREATE SERVICE ENTRY
    @PostMapping
    public ServiceEntry createServiceEntry(
            @RequestParam Long vehicleId,
            @RequestParam Long garageId,
            @RequestParam LocalDate serviceDate,
            @RequestParam Integer odometerReading,
            @RequestBody(required = false) List<ServicePart> parts) {

        return serviceEntryService.createServiceEntry(
                vehicleId, garageId, serviceDate, odometerReading, parts
        );
    }

    // GET ALL ENTRIES FOR VEHICLE
    @GetMapping("/vehicle/{vehicleId}")
    public List<ServiceEntry> getEntriesForVehicle(@PathVariable Long vehicleId) {
        return serviceEntryService.getEntriesForVehicle(vehicleId);
    }

    // GET ENTRIES FOR VEHICLE IN DATE RANGE
    @GetMapping("/vehicle/{vehicleId}/range")
    public List<ServiceEntry> getEntriesForVehicleInRange(
            @PathVariable Long vehicleId,
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {

        return serviceEntryService.getEntriesForVehicleInRange(vehicleId, start, end);
    }

    // GET ENTRIES FOR GARAGE ABOVE ODOMETER
    @GetMapping("/garage/{garageId}/odometer")
    public List<ServiceEntry> getEntriesForGarageAboveOdometer(
            @PathVariable Long garageId,
            @RequestParam Integer minOdometer) {

        return serviceEntryService.getEntriesForGarageAboveOdometer(garageId, minOdometer);
    }
}
