package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.ServiceEntry;
import com.example.demo.repository.ServiceEntryRepository;
import com.example.demo.service.ServiceEntryService;

@Service
public class ServiceEntryServiceImpl implements ServiceEntryService {

    private final ServiceEntryRepository serviceEntryRepository;

    public ServiceEntryServiceImpl(ServiceEntryRepository serviceEntryRepository) {
        this.serviceEntryRepository = serviceEntryRepository;
    }

    @Override
    public ServiceEntry save(ServiceEntry entry) {
        return serviceEntryRepository.save(entry);
    }

    @Override
    public List<ServiceEntry> getAll() {
        return serviceEntryRepository.findAll();
    }

    @Override
    public ServiceEntry getById(Long id) {
        return serviceEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ServiceEntry not found with id " + id));
    }

    @Override
    public void delete(Long id) {
        serviceEntryRepository.deleteById(id);
    }

    // ---------- REQUIRED BY INTERFACE ----------

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
            Integer odometer) {

        return serviceEntryRepository
                .findByGarageIdAndOdometerReadingGreaterThan(garageId, odometer);
    }
}
