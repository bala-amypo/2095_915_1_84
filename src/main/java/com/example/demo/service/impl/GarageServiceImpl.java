package com.example.demo.service.impl;

import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.model.Garage;
import com.example.demo.repository.GarageRepository;
import com.example.demo.service.GarageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GarageServiceImpl implements GarageService {

    private final GarageRepository garageRepository;

    public GarageServiceImpl(GarageRepository garageRepository) {
        this.garageRepository = garageRepository;
    }

    @Override
    public Garage createGarage(Garage garage) {
        if (garageRepository.findByGarageName(garage.getGarageName()).isPresent()) {
            throw new IllegalArgumentException("already exists");
        }
        garage.setActive(true);
        return garageRepository.save(garage);
    }

    @Override
    public Garage updateGarage(Long id, Garage garage) {
        Garage existing = getGarageById(id);
        existing.setAddress(garage.getAddress());
        existing.setContactNumber(garage.getContactNumber());
        return garageRepository.save(existing);
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

    @Override
    public void deactivateGarage(Long id) {
        Garage g = getGarageById(id);
        g.setActive(false);
        garageRepository.save(g);
    }
}
