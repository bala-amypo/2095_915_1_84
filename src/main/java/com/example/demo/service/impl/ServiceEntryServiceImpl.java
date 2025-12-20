package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ServiceEntryServiceImpl {

    private final ServiceEntryRepository repo;
    private final VehicleRepository vehicleRepo;
    private final GarageRepository garageRepo;

    public ServiceEntryServiceImpl(ServiceEntryRepository r, VehicleRepository v, GarageRepository g) {
        this.repo = r;
        this.vehicleRepo = v;
        this.garageRepo = g;
    }

    public ServiceEntry createServiceEntry(ServiceEntry e) {
        Vehicle v = vehicleRepo.findById(e.getVehicle().getId()).orElseThrow();
        Garage g = garageRepo.findById(e.getGarage().getId()).orElseThrow();

        if (!v.getActive()) throw new IllegalArgumentException("Only active vehicles allowed");
        if (e.getServiceDate().isAfter(LocalDate.now()))
            throw new IllegalArgumentException("future date not allowed");

        repo.findTopByVehicleOrderByOdometerReadingDesc(v)
                .ifPresent(last -> {
                    if (e.getOdometerReading() < last.getOdometerReading())
                        throw new IllegalArgumentException("Odometer must be >=");
                });

        return repo.save(e);
    }

    public List<ServiceEntry> getEntriesForVehicle(Long vehicleId) {
        return repo.findByVehicleId(vehicleId);
    }
}
