package com.example.demo.service.impl;

import com.example.demo.model.Garage;
import com.example.demo.repository.GarageRepository;
import org.springframework.stereotype.Service;

@Service
public class GarageServiceImpl {

    private final GarageRepository repo;

    public GarageServiceImpl(GarageRepository repo) {
        this.repo = repo;
    }

    public Garage createGarage(Garage g) {
        if (repo.findByGarageName(g.getGarageName()).isPresent())
            throw new IllegalArgumentException("Garage already exists");
        return repo.save(g);
    }
}
