package com.example.demo.service.impl;

import com.example.demo.model.Vehicle;
import com.example.demo.repository.VehicleRepository;
import com.example.demo.service.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Vehicle createVehicle(Vehicle vehicle) {
        vehicleRepository.findByVin(vehicle.getVin())
                .ifPresent(v -> {
                    throw new IllegalArgumentException("Vehicle with this VIN already exists");
                });

        vehicle.setActive(true);
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Vehicle not found")
                );
    }

    @Override
    public Vehicle getVehicleByVin(String vin) {
        return vehicleRepository.findByVin(vin)
                .orElseThrow(() ->
                        new EntityNotFoundException("Vehicle not found")
                );
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
