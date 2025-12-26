package com.example.demo.controller;

import com.example.demo.model.Garage;
import com.example.demo.service.GarageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/garages")
public class GarageController {

    private final GarageService garageService;

    // ✅ Constructor injection only
    public GarageController(GarageService garageService) {
        this.garageService = garageService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Garage createGarage(@RequestBody Garage garage) {
        return garageService.createGarage(garage);
    }

    @GetMapping("/{id}")
    public Garage getGarageById(@PathVariable Long id) {
        return garageService.getGarageById(id);
    }

    @GetMapping("/name/{name}")
    public Garage getGarageByName(@PathVariable String name) {
        return garageService.getGarageByName(name);
    }
}
