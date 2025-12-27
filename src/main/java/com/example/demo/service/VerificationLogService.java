package com.example.demo.service;

import com.example.demo.model.VerificationLog;

import java.util.List;

public interface VerificationLogService {

    VerificationLog createLog(VerificationLog log);

    VerificationLog getLogById(Long id);

    List<VerificationLog> getLogsForEntry(Long entryId);
}
