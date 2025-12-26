package com.example.demo.service;

import com.example.demo.model.ServiceEntry;
import com.example.demo.model.ServicePart;

import java.time.LocalDate;
import java.util.List;

public interface ServiceEntryService {

    ServiceEntry createServiceEntry(
            Long vehicleId,
            Long garageId,
            LocalDate serviceDate,
            Integer odometerReading,
            List<ServicePart> parts
    );

    List<ServiceEntry> getEntriesForVehicle(Long vehicleId);

    List<ServiceEntry> getEntriesForVehicleInRange(
            Long vehicleId,
            LocalDate from,
            LocalDate to
    );

    List<ServiceEntry> getEntriesForGarageAboveOdometer(
            Long garageId,
            Integer minOdometer
    );
}
