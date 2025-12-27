package com.example.demo.repository;

import com.example.demo.model.ServiceEntry;
import com.example.demo.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ServiceEntryRepository extends JpaRepository<ServiceEntry, Long> {
    List<ServiceEntry> findByVehicle_Id(Long vehicleId);
    Optional<ServiceEntry> findByIdAndVehicle_Id(Long id, Long vehicleId);
    Optional<ServiceEntry> findTopByVehicleOrderByOdometerReadingDesc(Vehicle vehicle);
    List<ServiceEntry> findByServiceDateBetween(LocalDate startDate, LocalDate endDate);
    List<ServiceEntry> findByGarage_Id(Long garageId);
    List<ServiceEntry> findByVehicle_IdAndServiceDateBetween(Long vehicleId, LocalDate startDate, LocalDate endDate);
}