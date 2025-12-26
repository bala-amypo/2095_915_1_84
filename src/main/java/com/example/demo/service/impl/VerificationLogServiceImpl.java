package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.VerificationLog;
import com.example.demo.repository.VerificationLogRepository;
import com.example.demo.service.VerificationLogService;

@Service
public class VerificationLogServiceImpl implements VerificationLogService {

    private final VerificationLogRepository verificationLogRepository;

    public VerificationLogServiceImpl(VerificationLogRepository verificationLogRepository) {
        this.verificationLogRepository = verificationLogRepository;
    }

    @Override
    public VerificationLog save(VerificationLog log) {
        return verificationLogRepository.save(log);
    }

    @Override
    public List<VerificationLog> getAll() {
        return verificationLogRepository.findAll();
    }

    @Override
    public VerificationLog getById(Long id) {
        return verificationLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VerificationLog not found with id " + id));
    }
}
