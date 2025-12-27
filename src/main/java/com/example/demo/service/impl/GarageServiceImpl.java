package com.example.demo.service.impl;

import com.example.demo.model.Garage;
import com.example.demo.repository.GarageRepository;
import com.example.demo.service.GarageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GarageServiceImpl implements GarageService {

    private final GarageRepository garageRepository;

    public GarageServiceImpl(GarageRepository garageRepository) {
        this.garageRepository = garageRepository;
    }

    @Override
    public Garage createGarage(Garage garage) {
        if (garageRepository.findByName(garage.getName()).isPresent()) {
            throw new IllegalArgumentException("Garage name already exists");
        }
        return garageRepository.save(garage);
    }

    @Override
    public Optional<Garage> getGarageById(Long id) {
        return garageRepository.findById(id);
    }

    @Override
    public List<Garage> getAllGarages() {
        return garageRepository.findAll();
    }

    @Override
    public Garage deactivateGarage(Long id) {
        Garage garage = garageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Garage not found"));

        garage.setActive(false);
        return garageRepository.save(garage);
    }
}
