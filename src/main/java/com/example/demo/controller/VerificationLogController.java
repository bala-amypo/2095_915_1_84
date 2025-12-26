package com.example.demo.controller;

import com.example.demo.model.VerificationLog;
import com.example.demo.service.VerificationLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/verification-logs")
public class VerificationLogController {
    private final VerificationLogService verificationLogService;

    public VerificationLogController(VerificationLogService verificationLogService) {
        this.verificationLogService = verificationLogService;
    }

    @PostMapping
    public ResponseEntity<VerificationLog> createVerificationLog(@RequestBody VerificationLog log) {
        return ResponseEntity.ok(verificationLogService.createLog(log));
    }

    @GetMapping("/service/{serviceEntryId}")
    public ResponseEntity<List<VerificationLog>> getLogsForService(@PathVariable Long serviceEntryId) {
        return ResponseEntity.ok(verificationLogService.getLogsForService(serviceEntryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VerificationLog> getVerificationLog(@PathVariable Long id) {
        return verificationLogService.getLogById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<VerificationLog>> getAllVerificationLogs() {
        return ResponseEntity.ok(verificationLogService.getAllLogs());
    }
}