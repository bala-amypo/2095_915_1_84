package com.example.demo.service.impl;

import com.example.demo.model.Garage;
import com.example.demo.repository.GarageRepository;
import com.example.demo.service.GarageService;
import org.springframework.stereotype.Service;

@Service
public class GarageServiceImpl implements GarageService {

    private final GarageRepository repo;

    public GarageServiceImpl(GarageRepository repo) {
        this.repo = repo;
    }

    public Garage createGarage(Garage g) {
        if (repo.findByGarageName(g.getGarageName()).isPresent()) {
            throw new IllegalArgumentException("Garage already exists");
        }
        return repo.save(g);
    }
}
