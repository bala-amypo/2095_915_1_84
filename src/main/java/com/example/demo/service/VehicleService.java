package com.example.demo.service;

import com.example.demo.model.Vehicle;

import java.util.List;

public interface VehicleService {

    Vehicle createVehicle(Vehicle vehicle);

    Vehicle getVehicleById(Long id);

    List<Vehicle> getVehiclesByOwner(Long ownerId);

    void deactivateVehicle(Long id);
}
