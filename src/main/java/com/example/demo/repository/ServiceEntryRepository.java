package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.ServiceEntry;
import com.example.demo.model.Vehicle;

public interface ServiceEntryRepository extends JpaRepository<ServiceEntry, Long> {

    Optional<ServiceEntry> findTopByVehicleOrderByOdometerReadingDesc(Vehicle vehicle);

    List<ServiceEntry> findByGarageIdAndOdometerReadingGreaterThan(
            Long garageId, int odometer);

    // aliases expected by tests
    default List<ServiceEntry> findByGarageAndMinOdometer(long garageId, int min) {
        return findByGarageIdAndOdometerReadingGreaterThan(garageId, min);
    }

    default List<ServiceEntry> findByVehicleAndDateRange(
            long vehicleId, LocalDate start, LocalDate end) {
        return findByVehicleIdAndServiceDateBetween(vehicleId, start, end);
    }

    List<ServiceEntry> findByVehicleIdAndServiceDateBetween(
            Long vehicleId, LocalDate start, LocalDate end);
}
