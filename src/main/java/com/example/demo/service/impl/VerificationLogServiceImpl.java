package com.example.demo.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.model.VerificationLog;
import com.example.demo.repository.VerificationLogRepository;
import com.example.demo.service.VerificationLogService;

@Service
public class VerificationLogServiceImpl implements VerificationLogService {

    private final VerificationLogRepository verificationLogRepository;

    public VerificationLogServiceImpl(
            VerificationLogRepository verificationLogRepository) {
        this.verificationLogRepository = verificationLogRepository;
    }

    // ✅ REQUIRED by VerificationLogService
    @Override
    public VerificationLog createLog(VerificationLog log) {
        return verificationLogRepository.save(log);
    }
}
