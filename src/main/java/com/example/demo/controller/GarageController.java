package com.example.demo.controller;

import com.example.demo.model.Garage;
import com.example.demo.service.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/garages")
public class GarageController {

    @Autowired
    private GarageService garageService;

    @PostMapping
    public Garage createGarage(@RequestBody Garage garage) {
        return garageService.createGarage(garage);
    }

    @GetMapping("/{id}")
    public Garage getGarageById(@PathVariable Long id) {
        return garageService.getGarageById(id);
    }

    @GetMapping
    public List<Garage> getAllGarages() {
        return garageService.getAllGarages();
    }
}
