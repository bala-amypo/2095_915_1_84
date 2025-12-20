package com.example.demo.service.impl;

import com.example.demo.model.Vehicle;
import com.example.demo.repository.VehicleRepository;
import com.example.demo.service.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public Vehicle createVehicle(Vehicle vehicle) {
        Optional<Vehicle> existing = vehicleRepository.findByVin(vehicle.getVin());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Vehicle with VIN already exists");
        }
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));
    }

    @Override
    public Vehicle getVehicleByVin(String vin) {
        return vehicleRepository.findByVin(vin)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found by VIN"));
    }

    @Override
    public List<Vehicle> getVehiclesByOwner(Long ownerId) {
        return vehicleRepository.findByOwnerId(ownerId);
    }

    @Override
    public void deactivateVehicle(Long id) {
        Vehicle vehicle = getVehicleById(id);
        vehicle.setActive(false);
        vehicleRepository.save(vehicle);
    }
}
