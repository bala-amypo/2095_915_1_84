package com.example.demo.repository;

import com.example.demo.model.VerificationLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VerificationLogRepository extends JpaRepository<VerificationLog, Long> {
    List<VerificationLog> findByServiceEntryId(Long serviceEntryId);
    List<VerificationLog> findByVerifiedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}