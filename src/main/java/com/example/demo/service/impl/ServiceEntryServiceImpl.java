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

    @Override
    public List<ServiceEntry> getAllServiceEntries() {
        return serviceEntryRepository.findAll();
    }

    @Override
    public ServiceEntry getServiceEntryById(Long id) {
        return serviceEntryRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("ServiceEntry not found"));
    }

    @Override
    public List<ServiceEntry> getServiceEntriesByVehicle(Long vehicleId) {
        return serviceEntryRepository.findByVehicle_Id(vehicleId);
    }

    @Override
    public ServiceEntry createServiceEntry(ServiceEntry entry, Long vehicleId) {

        Vehicle vehicle = vehicleRepository
                .findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

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

    @Override
    public ServiceEntry updateServiceEntry(Long id, ServiceEntry updatedEntry) {

        ServiceEntry existing = serviceEntryRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("ServiceEntry not found"));

        existing.setServiceDate(updatedEntry.getServiceDate());
        existing.setDescription(updatedEntry.getDescription());
        existing.setOdometerReading(updatedEntry.getOdometerReading());
        existing.setCost(updatedEntry.getCost());
        existing.setGarage(updatedEntry.getGarage());

        return serviceEntryRepository.save(existing);
    }

    @Override
    public void deleteServiceEntry(Long id) {
        if (!serviceEntryRepository.existsById(id)) {
            throw new RuntimeException("ServiceEntry not found");
        }
        serviceEntryRepository.deleteById(id);
    }

    @Override
    public List<ServiceEntry> getEntriesByDateRange(
            Long vehicleId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return serviceEntryRepository
                .findByVehicle_IdAndServiceDateBetween(
                        vehicleId, startDate, endDate
                );
    }

    @Override
    public ServiceEntry getLatestServiceEntry(Long vehicleId) {
        return serviceEntryRepository
                .findTopByVehicle_IdOrderByOdometerReadingDesc(vehicleId)
                .orElseThrow(() -> new RuntimeException("No service history found"));
    }
}
