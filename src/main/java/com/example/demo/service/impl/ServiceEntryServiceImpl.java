package com.example.demo.service.impl;

import com.example.demo.model.Garage;
import com.example.demo.model.ServiceEntry;
import com.example.demo.model.Vehicle;
import com.example.demo.repository.GarageRepository;
import com.example.demo.repository.ServiceEntryRepository;
import com.example.demo.repository.VehicleRepository;
import com.example.demo.service.ServiceEntryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceEntryServiceImpl implements ServiceEntryService {

    @Autowired
    private ServiceEntryRepository serviceEntryRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private GarageRepository garageRepository;

    @Override
    public ServiceEntry createServiceEntry(ServiceEntry entry) {
        Vehicle vehicle = vehicleRepository.findById(entry.getVehicle().getId())
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));
        if (!vehicle.getActive()) {
            throw new IllegalArgumentException("Cannot add service entry for inactive vehicles");
        }

        Garage garage = garageRepository.findById(entry.getGarage().getId())
                .orElseThrow(() -> new EntityNotFoundException("Garage not found"));

        Optional<ServiceEntry> lastEntry = serviceEntryRepository.findTopByVehicleOrderByOdometerReadingDesc(vehicle);
        if (lastEntry.isPresent() && entry.getOdometerReading() < lastEntry.get().getOdometerReading()) {
            throw new IllegalArgumentException("Odometer reading must be >= last recorded");
        }

        if (entry.getServiceDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Service date cannot be in the future");
        }

        entry.setVehicle(vehicle);
        entry.setGarage(garage);
        return serviceEntryRepository.save(entry);
    }

    @Override
    public List<ServiceEntry> getEntriesForVehicle(Long vehicleId) {
        return serviceEntryRepository.findByVehicleId(vehicleId);
    }
}
