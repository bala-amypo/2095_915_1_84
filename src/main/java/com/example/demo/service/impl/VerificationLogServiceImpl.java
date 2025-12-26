package com.example.demo.service.impl;

import com.example.demo.model.VerificationLog;
import com.example.demo.repository.VerificationLogRepository;
import com.example.demo.service.VerificationLogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VerificationLogServiceImpl implements VerificationLogService {
    private final VerificationLogRepository verificationLogRepository;

    public VerificationLogServiceImpl(VerificationLogRepository verificationLogRepository) {
        this.verificationLogRepository = verificationLogRepository;
    }

    @Override
    public VerificationLog createLog(VerificationLog log) {
        return verificationLogRepository.save(log);
    }

    @Override
    public Optional<VerificationLog> getLogById(Long id) {
        return verificationLogRepository.findById(id);
    }

    @Override
    public List<VerificationLog> getLogsForService(Long serviceEntryId) {
        return verificationLogRepository.findByServiceEntry_Id(serviceEntryId);
    }

    @Override
    public List<VerificationLog> getAllLogs() {
        return verificationLogRepository.findAll();
    }
}