package com.example.demo.repository;

import com.example.demo.model.VerificationLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerificationLogRepository extends JpaRepository<VerificationLog, Long> {

    // REQUIRED by tests
    List<VerificationLog> findByServiceEntryId(Long serviceEntryId);
}
