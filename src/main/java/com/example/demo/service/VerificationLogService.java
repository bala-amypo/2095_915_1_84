package com.example.demo.service;

import com.example.demo.model.VerificationLog;

import java.util.List;
import java.util.Optional;

public interface VerificationLogService {
    VerificationLog createLog(VerificationLog log);
    Optional<VerificationLog> getLogById(Long id);
    List<VerificationLog> getLogsForService(Long serviceEntryId);
    List<VerificationLog> getAllLogs();
}