package com.example.demo.repository;

import com.example.demo.model.ServiceEntry;
import com.example.demo.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ServiceEntryRepository extends JpaRepository<ServiceEntry, Long> {
    List<ServiceEntry> findByVehicleId(Long vehicleId);
    Optional<ServiceEntry> findByIdAndVehicleId(Long id, Long vehicleId);
    Optional<ServiceEntry> findTopByVehicleOrderByOdometerReadingDesc(Vehicle vehicle);
    List<ServiceEntry> findByServiceDateBetween(LocalDate startDate, LocalDate endDate);
    List<ServiceEntry> findByGarageAndMinOdometer(Long garageId, Integer minOdometer);
    List<ServiceEntry> findByVehicleAndDateRange(Long vehicleId, LocalDate startDate, LocalDate endDate);
}