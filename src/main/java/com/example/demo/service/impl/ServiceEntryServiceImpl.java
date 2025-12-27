package com.example.demo.service.impl;

import com.example.demo.model.ServiceEntry;
import com.example.demo.repository.ServiceEntryRepository;
import com.example.demo.service.ServiceEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceEntryServiceImpl implements ServiceEntryService {

    private final ServiceEntryRepository serviceEntryRepository;

    @Override
    public ServiceEntry createServiceEntry(ServiceEntry serviceEntry) {

        Long vehicleId = serviceEntry.getVehicleId();

        // ✅ FIXED: ID-based lookup
        Optional<ServiceEntry> lastEntry =
                serviceEntryRepository.findTopByVehicleIdOrderByOdometerReadingDesc(vehicleId);

        if (lastEntry.isPresent()) {
            if (serviceEntry.getOdometerReading()
                    <= lastEntry.get().getOdometerReading()) {
                throw new IllegalArgumentException(
                        "Odometer reading must be greater than last recorded reading");
            }
        }

        return serviceEntryRepository.save(serviceEntry);
    }

    @Override
    public List<ServiceEntry> getServiceEntriesByVehicle(Long vehicleId) {
        // ✅ FIXED
        return serviceEntryRepository.findByVehicleId(vehicleId);
    }
}
