package com.example.demo.service.impl;

import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.ServiceEntryService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ServiceEntryServiceImpl implements ServiceEntryService {

    private final VehicleRepository vehicleRepository;
    private final GarageRepository garageRepository;
    private final ServiceEntryRepository serviceEntryRepository;
    private final ServicePartRepository servicePartRepository;
    private final VerificationLogRepository verificationLogRepository;

    // ✅ Constructor injection ONLY
    public ServiceEntryServiceImpl(
            VehicleRepository vehicleRepository,
            GarageRepository garageRepository,
            ServiceEntryRepository serviceEntryRepository,
            ServicePartRepository servicePartRepository,
            VerificationLogRepository verificationLogRepository
    ) {
        this.vehicleRepository = vehicleRepository;
        this.garageRepository = garageRepository;
        this.serviceEntryRepository = serviceEntryRepository;
        this.servicePartRepository = servicePartRepository;
        this.verificationLogRepository = verificationLogRepository;
    }

    @Override
    public ServiceEntry createServiceEntry(
            Long vehicleId,
            Long garageId,
            LocalDate serviceDate,
            Integer odometerReading,
            List<ServicePart> parts
    ) {

        // -------- validation --------
        if (vehicleId == null || garageId == null || serviceDate == null || odometerReading == null) {
            throw new IllegalArgumentException("Invalid service entry data");
        }

        if (serviceDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Service date cannot be in the future");
        }

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));

        if (!vehicle.isActive()) {
            throw new IllegalArgumentException("Vehicle is inactive");
        }

        Garage garage = garageRepository.findById(garageId)
                .orElseThrow(() -> new EntityNotFoundException("Garage not found"));

        // -------- odometer monotonic check --------
        serviceEntryRepository
                .findTopByVehicleOrderByOdometerReadingDesc(vehicle)
                .ifPresent(last -> {
                    if (odometerReading <= last.getOdometerReading()) {
                        throw new IllegalArgumentException("Odometer reading must be greater than previous");
                    }
                });

        // -------- create service entry --------
        ServiceEntry entry = new ServiceEntry();
        entry.setVehicle(vehicle);
        entry.setGarage(garage);
        entry.setServiceDate(serviceDate);
        entry.setOdometerReading(odometerReading);

        ServiceEntry savedEntry = serviceEntryRepository.save(entry);

        // -------- parts (optional) --------
        if (parts != null) {
            for (ServicePart part : parts) {
                if (part.getQuantity() == null || part.getQuantity() <= 0) {
                    throw new IllegalArgumentException("Part quantity must be positive");
                }
                part.setServiceEntry(savedEntry);
                servicePartRepository.save(part);
            }
        }

        // -------- verification log (immutable ledger) --------
        VerificationLog log = new VerificationLog();
        log.setServiceEntry(savedEntry);
        log.setNotes("System verified entry");
        verificationLogRepository.save(log);

        return savedEntry;
    }

    @Override
    public List<ServiceEntry> getEntriesForVehicle(Long vehicleId) {
        if (vehicleId == null) {
            throw new IllegalArgumentException("VehicleId is required");
        }
        return serviceEntryRepository.findByVehicleId(vehicleId);
    }

    @Override
    public List<ServiceEntry> getEntriesForVehicleInRange(
            Long vehicleId,
            LocalDate from,
            LocalDate to
    ) {
        if (vehicleId == null || from == null || to == null) {
            throw new IllegalArgumentException("Invalid date range");
        }
        return serviceEntryRepository.findByVehicleAndDateRange(vehicleId, from, to);
    }

    @Override
    public List<ServiceEntry> getEntriesForGarageAboveOdometer(
            Long garageId,
            Integer minOdometer
    ) {
        if (garageId == null || minOdometer == null) {
            throw new IllegalArgumentException("Invalid odometer filter");
        }
        return serviceEntryRepository.findByGarageAndMinOdometer(garageId, minOdometer);
    }
}
