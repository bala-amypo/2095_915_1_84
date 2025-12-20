package com.example.demo.service.impl;

import com.example.demo.model.ServicePart;
import com.example.demo.repository.ServiceEntryRepository;
import com.example.demo.repository.ServicePartRepository;
import com.example.demo.service.ServicePartService;
import org.springframework.stereotype.Service;

@Service
public class ServicePartServiceImpl implements ServicePartService {

    private final ServicePartRepository partRepo;
    private final ServiceEntryRepository entryRepo;

    public ServicePartServiceImpl(ServicePartRepository partRepo,
                                  ServiceEntryRepository entryRepo) {
        this.partRepo = partRepo;
        this.entryRepo = entryRepo;
    }

    public ServicePart createPart(ServicePart part) {

        entryRepo.findById(part.getServiceEntry().getId()).orElseThrow();

        if (part.getQuantity() == null || part.getQuantity() <= 0)
            throw new IllegalArgumentException("Quantity must be positive");

        return partRepo.save(part);
    }
}
