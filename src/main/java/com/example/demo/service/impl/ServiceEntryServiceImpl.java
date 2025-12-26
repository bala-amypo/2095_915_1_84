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

    @Override
    public ServiceEntry createServiceEntry(
            Long vehicleId,
            Long garageId,
            LocalDate serviceDate,
            Integer odometerReading,
            List<ServicePart> parts) {

        ServiceEntry entry = new ServiceEntry();
        entry.setVehicleId(vehicleId);
        entry.setGarageId(garageId);
        entry.setServiceDate(serviceDate);
        entry.setOdometerReading(odometerReading);

        return serviceEntryRepository.save(entry);
    }

    @Override
    public List<ServiceEntry> getEntriesForVehicle(Long vehicleId) {
        return serviceEntryRepository.findByVehicleId(vehicleId);
    }

    @Override
    public List<ServiceEntry> getEntriesForVehicleInRange(
            Long vehicleId,
            LocalDate startDate,
            LocalDate endDate) {

        return serviceEntryRepository
                .findByVehicleIdAndServiceDateBetween(vehicleId, startDate, endDate);
    }

    @Override
    public List<ServiceEntry> getEntriesForGarageAboveOdometer(
            Long garageId,
            Integer minOdometer) {

        return serviceEntryRepository
                .findByGarageIdAndOdometerReadingGreaterThan(garageId, minOdometer);
    }
}
