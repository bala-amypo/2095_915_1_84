package com.example.demo.service.impl;

import com.example.demo.model.Garage;
import com.example.demo.repository.GarageRepository;
import com.example.demo.service.GarageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GarageServiceImpl implements GarageService {

    @Autowired
    private GarageRepository garageRepository;

    @Override
    public Garage createGarage(Garage garage) {
        Optional<Garage> existing = garageRepository.findByGarageName(garage.getGarageName());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Garage already exists");
        }
        return garageRepository.save(garage);
    }

    @Override
    public Garage getGarageById(Long id) {
        return garageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Garage not found"));
    }

    @Override
    public List<Garage> getAllGarages() {
        return garageRepository.findAll();
    }
}
