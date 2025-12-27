package com.example.demo.service;

import com.example.demo.model.Garage;

import java.util.List;

public interface GarageService {

    Garage createGarage(Garage garage);

    Garage updateGarage(Long id, Garage garage);

    Garage getGarageById(Long id);

    List<Garage> getAllGarages();

    void deactivateGarage(Long id);
}
