package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.ServiceEntry;
import com.example.demo.repository.ServiceEntryRepository;
import com.example.demo.service.ServiceEntryService;

@Service
public class ServiceEntryServiceImpl implements ServiceEntryService {

    private final ServiceEntryRepository repository;

    public ServiceEntryServiceImpl(ServiceEntryRepository repository) {
        this.repository = repository;
    }

    @Override
    public ServiceEntry createServiceEntry(ServiceEntry entry) {
        return repository.save(entry);
    }

    @Override
    public List<ServiceEntry> getEntriesForVehicle(Long vehicleId) {
        return repository.findAll().stream()
                .filter(e -> e.getVehicle().getId().equals(vehicleId))
                .toList();
    }
}
