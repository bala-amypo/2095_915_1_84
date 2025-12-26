package com.example.demo.controller;

import com.example.demo.model.Vehicle;
import com.example.demo.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(vehicleService.createVehicle(vehicle));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable Long id) {
        return vehicleService.getVehicleById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Vehicle>> getVehiclesByOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(vehicleService.getVehiclesByOwner(ownerId));
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Vehicle> deactivateVehicle(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.deactivateVehicle(id));
    }
}