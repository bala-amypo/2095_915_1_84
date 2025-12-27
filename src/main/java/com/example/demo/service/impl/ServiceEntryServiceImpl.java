package com.example.demo.service.impl;

import com.example.demo.model.ServiceEntry;
import com.example.demo.model.Vehicle;
import com.example.demo.repository.ServiceEntryRepository;
import com.example.demo.repository.VehicleRepository;
import com.example.demo.service.ServiceEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ServiceEntryServiceImpl implements ServiceEntryService {

    @Autowired
    private ServiceEntryRepository serviceEntryRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    // ===== REQUIRED BY TESTS =====
    public List<ServiceEntry> getEntriesForVehicle(long vehicleId) {
        return serviceEntryRepository.findByVehicle_Id(vehicleId);
    }

    // ===== Create Service Entry =====
    public ServiceEntry createServiceEntry(ServiceEntry entry, long vehicleId) {

        Vehicle vehicle = vehicleRepository
                .findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        // Validate odometer monotonic increase
        serviceEntryRepository
                .findTopByVehicle_IdOrderByOdometerReadingDesc(vehicleId)
                .ifPresent(last -> {
                    if (entry.getOdometerReading() <= last.getOdometerReading()) {
                        throw new IllegalArgumentException(
                                "Odometer reading must be greater than last recorded value"
                        );
                    }
                });

        entry.setVehicle(vehicle);
        return serviceEntryRepository.save(entry);
    }

    // ===== Date Range =====
    public List<ServiceEntry> getEntriesByDateRange(
            long vehicleId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return serviceEntryRepository.findByVehicleAndDateRange(
                vehicleId, startDate, endDate
        );
    }

    // ===== Latest Entry =====
    public ServiceEntry getLatestServiceEntry(long vehicleId) {
        return serviceEntryRepository
                .findTopByVehicle_IdOrderByOdometerReadingDesc(vehicleId)
                .orElseThrow(() -> new RuntimeException("No service history found"));
    }
}
