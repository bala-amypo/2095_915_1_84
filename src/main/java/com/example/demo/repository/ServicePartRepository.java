package com.example.demo.repository;

import com.example.demo.model.ServicePart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicePartRepository extends JpaRepository<ServicePart, Long> {

    // REQUIRED by tests
    List<ServicePart> findByServiceEntryId(Long serviceEntryId);
}
