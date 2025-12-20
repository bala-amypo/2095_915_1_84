package com.example.demo.service.impl;

import com.example.demo.model.ServicePart;
import com.example.demo.model.ServiceEntry;
import com.example.demo.repository.ServiceEntryRepository;
import com.example.demo.repository.ServicePartRepository;
import com.example.demo.service.ServicePartService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicePartServiceImpl implements ServicePartService {

    @Autowired
    private ServicePartRepository servicePartRepository;

    @Autowired
    private ServiceEntryRepository serviceEntryRepository;

    @Override
    public ServicePart createPart(ServicePart part) {
        ServiceEntry entry = serviceEntryRepository.findById(part.getServiceEntry().getId())
                .orElseThrow(() -> new EntityNotFoundException("ServiceEntry not found"));

        if (part.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        part.setServiceEntry(entry);
        return servicePartRepository.save(part);
    }
}
