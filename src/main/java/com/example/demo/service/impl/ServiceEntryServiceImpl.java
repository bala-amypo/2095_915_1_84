package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.ServiceEntry;
import com.example.demo.model.ServicePart;
import com.example.demo.repository.ServiceEntryRepository;
import com.example.demo.service.ServiceEntryService;

@Service
public class ServiceEntryServiceImpl implements ServiceEntryService {

    private final ServiceEntryRepository serviceEntryRepository;

    public ServiceEntryServiceImpl(ServiceEntryRepository serviceEntryRepository) {
        this.serviceEntryRepository = serviceEntryRepository;
    }

    // ✅ REQUIRED by ServiceEntryService
    @Override
    public ServiceEntry createServiceEntry(
            Long vehicleId,
            Long garageId,
            LocalDate serviceDate,
            Integer odometerReading,
            List<ServicePart> parts
    ) {
        ServiceEntry entry = new ServiceEntry();
        entry.setVehicleId(vehicleId);
        entry.setGarageId(garageId);
        entry.setServiceDate(serviceDate);
        entry.setOdometerReading(odometerReading);

        // Parts are ignored here because your ServiceEntry entity
        // currently does NOT define a relationship
        return serviceEntryRepository.save(entry);
    }

    // ✅ REQUIRED by ServiceEntryService
    @Override
    public List<ServiceEntry> getEntriesForVehicle(Long vehicleId) {
        return serviceEntryRepository.findByVehicleId(vehicleId);
    }

    // ✅ REQUIRED by ServiceEntryService
    @Override
    public List<ServiceEntry> getEntriesForVehicleInRange(
            Long vehicleId,
            LocalDate start,
            LocalDate end
    ) {
        return serviceEntryRepository
                .findByVehicleIdAndServiceDateBetween(vehicleId, start, end);
    }

    // ✅ REQUIRED by ServiceEntryService
    @Override
    public List<ServiceEntry> getEntriesForGarageAboveOdometer(
            Long garageId,
            Integer odometer
    ) {
        return serviceEntryRepository
                .findByGarageIdAndOdometerReadingGreaterThan(garageId, odometer);
    }
}
