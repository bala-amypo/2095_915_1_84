package com.example.demo.service;

import com.example.demo.model.ServiceEntry;
import com.example.demo.model.Vehicle;

import java.util.List;

public interface ServiceEntryService {

    ServiceEntry createServiceEntry(ServiceEntry entry);

    List<ServiceEntry> getEntriesForVehicle(Long vehicleId);

    ServiceEntry getLatestServiceEntry(Vehicle vehicle);
}
