package com.example.demo.repository;

import com.example.demo.model.ServiceEntry;
import com.example.demo.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;   // ✅ REQUIRED IMPORT

public interface ServiceEntryRepository
        extends JpaRepository<ServiceEntry, Long> {

    List<ServiceEntry> findByVehicleId(Long vehicleId);

    ServiceEntry findTopByVehicleOrderByOdometerReadingDesc(Vehicle vehicle);
}
