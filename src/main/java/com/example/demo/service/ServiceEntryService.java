package com.example.demo.service;

import com.example.demo.model.ServiceEntry;

import java.util.List;

public interface ServiceEntryService {

    ServiceEntry createServiceEntry(ServiceEntry entry);

    ServiceEntry updateServiceEntry(Long id, ServiceEntry entry);

    void deleteServiceEntry(Long id);

    ServiceEntry getServiceEntryById(Long id);

    ServiceEntry getLatestServiceEntry(Long vehicleId);

    List<ServiceEntry> getServiceEntriesByVehicle(Long vehicleId);

    List<ServiceEntry> getAllServiceEntries();
}
