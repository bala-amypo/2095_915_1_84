package com.example.demo.service.impl;

import com.example.demo.model.ServiceEntry;
import com.example.demo.repository.ServiceEntryRepository;
import com.example.demo.service.ServiceEntryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceEntryServiceImpl implements ServiceEntryService {

    private final ServiceEntryRepository serviceEntryRepository;

    public ServiceEntryServiceImpl(ServiceEntryRepository serviceEntryRepository) {
        this.serviceEntryRepository = serviceEntryRepository;
    }

    @Override
    public ServiceEntry getLatestServiceEntry(Long vehicleId) {
        return serviceEntryRepository
                .findTopByVehicle_IdOrderByOdometerReadingDesc(vehicleId)
                .orElse(null);
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
    public ServiceEntry createServiceEntry(ServiceEntry entry) {
        return serviceEntryRepository.save(entry);
    }

    @Override
    public ServiceEntry updateServiceEntry(Long id, ServiceEntry entry) {
        ServiceEntry existing = serviceEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ServiceEntry not found"));

        existing.setServiceDate(entry.getServiceDate());
        existing.setOdometerReading(entry.getOdometerReading());
        existing.setGarage(entry.getGarage());

        return serviceEntryRepository.save(existing);
    }
}
