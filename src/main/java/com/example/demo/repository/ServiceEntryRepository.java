package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ServiceEntry;

@Repository
public interface ServiceEntryRepository extends JpaRepository<ServiceEntry, Long> {

    // Get all service entries for a vehicle
    List<ServiceEntry> findByVehicleId(Long vehicleId);

    // Get service entries for a vehicle between dates
    List<ServiceEntry> findByVehicleIdAndServiceDateBetween(
            Long vehicleId,
            LocalDate startDate,
            LocalDate endDate
    );

    // Get service entries for a garage where odometer reading is above a value
    List<ServiceEntry> findByGarageIdAndOdometerReadingGreaterThan(
            Long garageId,
            Integer odometer
    );
}
