package com.example.demo.service.impl;

import com.example.demo.model.ServiceEntry;
import com.example.demo.model.Vehicle;
import com.example.demo.repository.ServiceEntryRepository;
import com.example.demo.service.ServiceEntryService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceEntryServiceImpl implements ServiceEntryService {

    private final ServiceEntryRepository repository;

    public ServiceEntryServiceImpl(ServiceEntryRepository repository) {
        this.repository = repository;
    }

    @Override
    public ServiceEntry createServiceEntry(ServiceEntry entry) {

        Vehicle v = entry.getVehicle();

        if (!v.getActive()) {
            throw new RuntimeException("Vehicle is inactive");
        }

        ServiceEntry last =
                repository.findTopByVehicleOrderByOdometerReadingDesc(v);

        if (last != null &&
            entry.getOdometerReading() <= last.getOdometerReading()) {

            throw new RuntimeException(
                    "Odometer reading must be greater than last service"
            );
        }

        return repository.save(entry);
    }

    @Override
    public List<ServiceEntry> getEntriesForVehicle(Long vehicleId) {
        return repository.findByVehicleId(vehicleId);
    }
}
