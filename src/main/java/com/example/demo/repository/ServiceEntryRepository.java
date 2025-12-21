package com.example.demo.repository;

import com.example.demo.entity.ServiceEntry;
import com.example.demo.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceEntryRepository extends JpaRepository<ServiceEntry, Long> {

    // Get all service entries for a vehicle
    List<ServiceEntry> findByVehicleId(Long vehicleId);

    // Get latest service entry by odometer for a vehicle
    ServiceEntry findTopByVehicleOrderByOdometerReadingDesc(Vehicle vehicle);

    // Get service entries by garage with odometer greater than value
    List<ServiceEntry> findByGarageIdAndOdometerReadingGreaterThan(
            Long garageId,
            Integer odometerReading
    );
}
