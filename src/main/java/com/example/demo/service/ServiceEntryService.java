package com.example.demo.service;

import java.util.List;

import com.example.demo.model.ServiceEntry;

public interface ServiceEntryService {

    ServiceEntry createServiceEntry(ServiceEntry entry);

    List<ServiceEntry> getEntriesForVehicle(Long vehicleId);
}
