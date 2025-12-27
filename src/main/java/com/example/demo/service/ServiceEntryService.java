package com.example.demo.service;

import com.example.demo.model.ServiceEntry;

import java.util.List;

public interface ServiceEntryService {

    ServiceEntry createServiceEntry(ServiceEntry serviceEntry);

    ServiceEntry updateServiceEntry(Long id, ServiceEntry serviceEntry);

    ServiceEntry getServiceEntryById(Long id);

    List<ServiceEntry> getServiceEntriesByVehicle(Long vehicleId);

    void deleteServiceEntry(Long id);
}
