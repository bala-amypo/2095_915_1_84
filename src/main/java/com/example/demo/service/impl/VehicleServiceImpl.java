package com.example.demo.service.impl;

import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.model.Vehicle;
import com.example.demo.repository.VehicleRepository;
import com.example.demo.service.VehicleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    // ✅ Constructor injection ONLY
    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Vehicle createVehicle(Vehicle vehicle) {
        if (vehicle == null || vehicle.getVin() == null || vehicle.getVin().isBlank()) {
            throw new IllegalArgumentException("VIN is required");
        }

        vehicleRepository.findByVin(vehicle.getVin())
                .ifPresent(v -> {
                    throw new IllegalArgumentException("VIN already exists");
                });

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
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));
    }

    @Override
    public List<Vehicle> getVehiclesByOwner(Long ownerId) {
        if (ownerId == null) {
            throw new IllegalArgumentException("OwnerId is required");
        }
        return vehicleRepository.findByOwnerId(ownerId);
    }

    @Override
    public void deactivateVehicle(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));

        vehicle.setActive(false);
        vehicleRepository.save(vehicle);
    }
}
