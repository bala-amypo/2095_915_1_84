package com.example.demo.service;

import com.example.demo.model.ServiceEntry;

import java.util.List;
import java.util.Optional;

public interface ServiceEntryService {
    ServiceEntry createServiceEntry(ServiceEntry entry);
    Optional<ServiceEntry> getServiceEntryById(Long id);
    List<ServiceEntry> getEntriesForVehicle(Long vehicleId);
    List<ServiceEntry> getAllServiceEntries();
    ServiceEntry updateServiceEntry(Long id, ServiceEntry entry);
}