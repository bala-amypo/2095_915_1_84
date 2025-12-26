package com.example.demo.service.impl;

import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.model.Garage;
import com.example.demo.repository.GarageRepository;
import com.example.demo.service.GarageService;
import org.springframework.stereotype.Service;

@Service
public class GarageServiceImpl implements GarageService {

    private final GarageRepository garageRepository;

    // ✅ Constructor injection only
    public GarageServiceImpl(GarageRepository garageRepository) {
        this.garageRepository = garageRepository;
    }

    @Override
    public Garage createGarage(Garage garage) {
        if (garage == null || garage.getGarageName() == null || garage.getGarageName().isBlank()) {
            throw new IllegalArgumentException("Garage name is required");
        }

        garageRepository.findByGarageName(garage.getGarageName())
                .ifPresent(g -> {
                    throw new IllegalArgumentException("Garage already exists");
                });

        return garageRepository.save(garage);
    }

    @Override
    public Garage getGarageById(Long id) {
        return garageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Garage not found"));
    }

    @Override
    public Garage getGarageByName(String name) {
        return garageRepository.findByGarageName(name)
                .orElseThrow(() -> new EntityNotFoundException("Garage not found"));
    }
}
