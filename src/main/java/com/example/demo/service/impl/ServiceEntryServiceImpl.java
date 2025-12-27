package com.example.demo.service.impl;

import com.example.demo.model.ServiceEntry;
import com.example.demo.repository.ServiceEntryRepository;
import com.example.demo.service.ServiceEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceEntryServiceImpl implements ServiceEntryService {

    private final ServiceEntryRepository serviceEntryRepository;

    @Override
    public ServiceEntry createServiceEntry(ServiceEntry serviceEntry) {

        Long vehicleId = serviceEntry.getVehicleId();

        serviceEntryRepository
                .findTopByVehicleIdOrderByOdometerReadingDesc(vehicleId)
                .ifPresent(last -> {
                    if (serviceEntry.getOdometerReading()
                            <= last.getOdometerReading()) {
                        throw new IllegalArgumentException(
                                "Odometer reading must be greater than last recorded value");
                    }
                });

        return serviceEntryRepository.save(serviceEntry);
    }

    @Override
    public ServiceEntry updateServiceEntry(Long id, ServiceEntry serviceEntry) {

        ServiceEntry existing = serviceEntryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("ServiceEntry not found with id: " + id));

        existing.setServiceDate(serviceEntry.getServiceDate());
        existing.setOdometerReading(serviceEntry.getOdometerReading());
        existing.setGarageId(serviceEntry.getGarageId());

        return serviceEntryRepository.save(existing);
    }

    @Override
    public ServiceEntry getServiceEntryById(Long id) {
        return serviceEntryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("ServiceEntry not found with id: " + id));
    }

    @Override
    public List<ServiceEntry> getServiceEntriesByVehicle(Long vehicleId) {
        return serviceEntryRepository.findByVehicleId(vehicleId);
    }

    @Override
    public void deleteServiceEntry(Long id) {
        if (!serviceEntryRepository.existsById(id)) {
            throw new RuntimeException("ServiceEntry not found with id: " + id);
        }
        serviceEntryRepository.deleteById(id);
    }
}
