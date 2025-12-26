package com.example.demo.service;

import com.example.demo.model.Garage;

import java.util.List;
import java.util.Optional;

public interface GarageService {
    Garage createGarage(Garage garage);
    Optional<Garage> getGarageById(Long id);
    List<Garage> getAllGarages();
    Garage deactivateGarage(Long id);
}