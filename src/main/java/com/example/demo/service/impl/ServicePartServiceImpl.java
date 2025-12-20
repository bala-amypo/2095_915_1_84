package com.example.demo.service.impl;

import com.example.demo.model.ServicePart;
import com.example.demo.repository.ServiceEntryRepository;
import com.example.demo.repository.ServicePartRepository;
import org.springframework.stereotype.Service;

@Service
public class ServicePartServiceImpl {

    private final ServicePartRepository repo;
    private final ServiceEntryRepository entryRepo;

    public ServicePartServiceImpl(ServicePartRepository r, ServiceEntryRepository e) {
        this.repo = r;
        this.entryRepo = e;
    }

    public ServicePart createPart(ServicePart p) {
        if (p.getQuantity() == null || p.getQuantity() <= 0)
            throw new IllegalArgumentException("Quantity must be positive");
        entryRepo.findById(p.getServiceEntry().getId()).orElseThrow();
        return repo.save(p);
    }
}
