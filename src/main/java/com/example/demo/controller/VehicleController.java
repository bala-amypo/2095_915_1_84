package com.example.demo.controller;

import com.example.demo.model.Vehicle;
import com.example.demo.service.VehicleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@Tag(name = "Vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    // CREATE VEHICLE
    @PostMapping
    public ResponseEntity<Vehicle> create(@RequestBody Vehicle vehicle) {
        try {
            return new ResponseEntity<>(
                    vehicleService.createVehicle(vehicle),
                    HttpStatus.CREATED
            );
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    ex.getMessage()
            );
        }
    }

    // GET VEHICLE BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(vehicleService.getVehicleById(id));
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    ex.getMessage()
            );
        }
    }

    // GET VEHICLE BY VIN
    @GetMapping("/vin/{vin}")
    public ResponseEntity<Vehicle> getByVin(@PathVariable String vin) {
        try {
            return ResponseEntity.ok(vehicleService.getVehicleByVin(vin));
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    ex.getMessage()
            );
        }
    }

    // GET VEHICLES BY OWNER
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Vehicle>> getByOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(vehicleService.getVehiclesByOwner(ownerId));
    }

    // DEACTIVATE VEHICLE
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        try {
            vehicleService.deactivateVehicle(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    ex.getMessage()
            );
        }
    }
}
