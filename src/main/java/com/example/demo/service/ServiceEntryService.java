package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.model.ServiceEntry;
import com.example.demo.model.ServicePart;

public interface ServiceEntryService {

    // CREATE SERVICE ENTRY
    ServiceEntry createServiceEntry(
            Long vehicleId,
            Long garageId,
            LocalDate serviceDate,
            Integer odometerReading,
            List<ServicePart> parts
    );

    // GET SERVICE ENTRIES FOR VEHICLE BETWEEN DATES
    List<ServiceEntry> getEntriesForVehicleInRange(
            Long vehicleId,
            LocalDate startDate,
            LocalDate endDate
    );

    // GET SERVICE ENTRIES FOR GARAGE ABOVE ODOMETER
    List<ServiceEntry> getEntriesForGarageAboveOdometer(
            Long garageId,
            Integer minOdometer
    );
}
