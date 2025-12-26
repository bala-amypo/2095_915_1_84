package com.example.demo.service;

import com.example.demo.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleService {
    Vehicle createVehicle(Vehicle vehicle);
    Optional<Vehicle> getVehicleById(Long id);
    List<Vehicle> getAllVehicles();
    List<Vehicle> getVehiclesByOwner(Long ownerId);
    Vehicle deactivateVehicle(Long id);
    Vehicle getVehicleByVin(String vin);
}