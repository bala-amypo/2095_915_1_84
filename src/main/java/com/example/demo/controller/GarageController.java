package com.example.demo.controller;

import com.example.demo.model.Garage;
import com.example.demo.service.GarageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/garages")
@Tag(name = "Garages")
public class GarageController {

    private final GarageService garageService;

    public GarageController(GarageService garageService) {
        this.garageService = garageService;
    }

    @PostMapping
    public Garage create(@RequestBody Garage garage) {
        return garageService.createGarage(garage);
    }

    @PutMapping("/{id}")
    public Garage update(@PathVariable Long id, @RequestBody Garage garage) {
        return garageService.updateGarage(id, garage);
    }

    @GetMapping("/{id}")
    public Garage getById(@PathVariable Long id) {
        return garageService.getGarageById(id);
    }

    @GetMapping
    public List<Garage> getAll() {
        return garageService.getAllGarages();
    }

    @PutMapping("/{id}/deactivate")
    public void deactivate(@PathVariable Long id) {
        garageService.deactivateGarage(id);
    }
}
