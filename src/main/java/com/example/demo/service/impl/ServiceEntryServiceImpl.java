package com.example.demo.service.impl;

import com.example.demo.model.ServiceEntry;
import com.example.demo.model.Vehicle;
import com.example.demo.repository.ServiceEntryRepository;
import com.example.demo.service.ServiceEntryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ServiceEntryServiceImpl implements ServiceEntryService {

    private final ServiceEntryRepository serviceEntryRepository;

    public ServiceEntryServiceImpl(ServiceEntryRepository serviceEntryRepository) {
        this.serviceEntryRepository = serviceEntryRepository;
    }

    @Override
    public ServiceEntry getLatestServiceEntry(Vehicle vehicle) {
        return serviceEntryRepository
                .findTopByVehicleOrderByOdometerReadingDesc(vehicle)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "No ServiceEntry found for vehicle id: " + vehicle.getId()
                        )
                );
    }
}
