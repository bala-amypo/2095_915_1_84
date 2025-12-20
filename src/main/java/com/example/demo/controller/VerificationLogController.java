package com.example.demo.controller;

import com.example.demo.model.VerificationLog;
import com.example.demo.service.VerificationLogService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verification-logs")
public class VerificationLogController {

    private final VerificationLogService service;

    public VerificationLogController(VerificationLogService service) {
        this.service = service;
    }

    @PostMapping
    public VerificationLog create(@RequestBody VerificationLog log) {
        return service.createLog(log);
    }
}
