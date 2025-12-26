package com.example.demo.controller;

import com.example.demo.model.Garage;
import com.example.demo.service.GarageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/garages")
public class GarageController {
    private final GarageService garageService;

    public GarageController(GarageService garageService) {
        this.garageService = garageService;
    }

    @PostMapping
    public ResponseEntity<Garage> createGarage(@RequestBody Garage garage) {
        return ResponseEntity.ok(garageService.createGarage(garage));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Garage> getGarage(@PathVariable Long id) {
        return garageService.getGarageById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Garage>> getAllGarages() {
        return ResponseEntity.ok(garageService.getAllGarages());
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Garage> deactivateGarage(@PathVariable Long id) {
        return ResponseEntity.ok(garageService.deactivateGarage(id));
    }
}