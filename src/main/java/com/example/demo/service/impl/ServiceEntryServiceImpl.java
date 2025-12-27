
package com.example.demo.service.impl;

import com.example.demo.model.Garage;
import com.example.demo.model.ServiceEntry;
import com.example.demo.model.Vehicle;
import com.example.demo.repository.GarageRepository;
import com.example.demo.repository.ServiceEntryRepository;
import com.example.demo.repository.VehicleRepository;
import com.example.demo.service.ServiceEntryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceEntryServiceImpl implements ServiceEntryService {

    private final ServiceEntryRepository serviceEntryRepository;
    private final VehicleRepository vehicleRepository;
    private final GarageRepository garageRepository;

    public ServiceEntryServiceImpl(ServiceEntryRepository serviceEntryRepository,
                                  VehicleRepository vehicleRepository,
                                  GarageRepository garageRepository) {
        this.serviceEntryRepository = serviceEntryRepository;
        this.vehicleRepository = vehicleRepository;
        this.garageRepository = garageRepository;
    }

    @Override
    public ServiceEntry createServiceEntry(ServiceEntry entry) {

        if (entry.getVehicle() == null || entry.getVehicle().getId() == null) {
            throw new IllegalArgumentException("Vehicle is required");
        }

        if (entry.getGarage() == null || entry.getGarage().getId() == null) {
            throw new IllegalArgumentException("Garage is required");
        }

        Vehicle vehicle = vehicleRepository.findById(entry.getVehicle().getId())
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));

        if (!Boolean.TRUE.equals(vehicle.getActive())) {
            throw new IllegalArgumentException("Only active vehicles allowed");
        }

        Garage garage = garageRepository.findById(entry.getGarage().getId())
                .orElseThrow(() -> new EntityNotFoundException("Garage not found"));

        if (!Boolean.TRUE.equals(garage.getActive())) {
            throw new IllegalArgumentException("Garage not active");
        }

        if (entry.getServiceDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Service date cannot be in the future");
        }

        Optional<ServiceEntry> lastService =
                serviceEntryRepository.findTopByVehicleOrderByOdometerReadingDesc(vehicle);

        if (lastService.isPresent()
                && entry.getOdometerReading() < lastService.get().getOdometerReading()) {
            throw new IllegalArgumentException(
                    "Odometer reading must be greater than or equal to previous reading");
        }

        // IMPORTANT: reattach managed entities
        entry.setVehicle(vehicle);
        entry.setGarage(garage);

        return serviceEntryRepository.save(entry);
    }

    @Override
    public Optional<ServiceEntry> getServiceEntryById(Long id) {
        return serviceEntryRepository.findById(id);
    }

    @Override
    public List<ServiceEntry> getEntriesForVehicle(Long vehicleId) {
        return serviceEntryRepository.findByVehicle_Id(vehicleId);
    }

    @Override
    public List<ServiceEntry> getAllServiceEntries() {
        return serviceEntryRepository.findAll();
    }

    @Override
    public ServiceEntry updateServiceEntry(Long id, ServiceEntry entry) {

        ServiceEntry existing = serviceEntryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service entry not found"));

        if (entry.getServiceDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Service date cannot be in the future");
        }

        if (entry.getOdometerReading() < existing.getOdometerReading()) {
            throw new IllegalArgumentException("Odometer reading cannot decrease");
        }

        existing.setServiceType(entry.getServiceType());
        existing.setServiceDate(entry.getServiceDate());
        existing.setOdometerReading(entry.getOdometerReading());
        existing.setCost(entry.getCost());
        existing.setNotes(entry.getNotes());

        return serviceEntryRepository.save(existing);
    }
}
