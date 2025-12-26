package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.ServiceEntry;
import com.example.demo.model.Vehicle;

public interface ServiceEntryRepository extends JpaRepository<ServiceEntry, Long> {

    Optional<ServiceEntry> findTopByVehicleOrderByOdometerReadingDesc(Vehicle vehicle);

    List<ServiceEntry> findByVehicleAndServiceDateBetween(
            Vehicle vehicle,
            LocalDate start,
            LocalDate end
    );

    List<ServiceEntry> findByGarageIdAndOdometerReadingGreaterThan(
            Long garageId,
            Integer minOdometer
    );

    // 🔁 aliases required by tests
    default List<ServiceEntry> findByVehicleAndDateRange(
            long vehicleId, LocalDate start, LocalDate end) {
        return findAll().stream()
                .filter(e -> e.getVehicle().getId() == vehicleId)
                .filter(e -> !e.getServiceDate().isBefore(start)
                        && !e.getServiceDate().isAfter(end))
                .toList();
    }

    default List<ServiceEntry> findByGarageAndMinOdometer(
            long garageId, int minOdometer) {
        return findByGarageIdAndOdometerReadingGreaterThan(garageId, minOdometer);
    }
}
