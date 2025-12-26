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

    // ✅ REQUIRED BY INTERFACE
    @Override
    public ServiceEntry createServiceEntry(
            Long vehicleId,
            Long garageId,
            LocalDate serviceDate,
            Integer odometer,
            List<ServicePart> parts) {

        ServiceEntry entry = new ServiceEntry();
        entry.setVehicleId(vehicleId);
        entry.setGarageId(garageId);
        entry.setServiceDate(serviceDate);
        entry.setOdometerReading(odometer);

        return serviceEntryRepository.save(entry);
    }

    // ✅ REQUIRED BY INTERFACE
    @Override
    public List<ServiceEntry> getEntriesForVehicle(Long vehicleId) {
        return serviceEntryRepository.findByVehicleId(vehicleId);
    }

    // ✅ REQUIRED BY INTERFACE
    @Override
    public List<ServiceEntry> getEntriesForVehicleInRange(
            Long vehicleId,
            LocalDate start,
            LocalDate end) {

        return serviceEntryRepository
                .findByVehicleIdAndServiceDateBetween(vehicleId, start, end);
    }

    // ✅ REQUIRED BY INTERFACE
    @Override
    public List<ServiceEntry> getEntriesForGarageAboveOdometer(
            Long garageId,
            Integer odometer) {

        return serviceEntryRepository
                .findByGarageIdAndOdometerReadingGreaterThan(garageId, odometer);
    }
}
