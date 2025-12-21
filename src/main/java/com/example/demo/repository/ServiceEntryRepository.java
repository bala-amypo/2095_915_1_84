package com.example.demo.repository;

import com.example.demo.model.ServiceEntry;
import com.example.demo.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceEntryRepository
        extends JpaRepository<ServiceEntry, Long> {

    ServiceEntry findTopByVehicleOrderByOdometerReadingDesc(Vehicle vehicle);
    List<ServiceEntry> findByVehicleId(Long vehicleId);

}
