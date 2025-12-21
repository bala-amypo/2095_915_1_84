package com.example.demo.repository;

import com.example.demo.entity.ServiceEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceEntryRepository extends JpaRepository<ServiceEntry, Long> {


    List<ServiceEntry> findByGarageIdAndOdometerGreaterThan(
            Long garageId,
            Integer odometer
    );
}
