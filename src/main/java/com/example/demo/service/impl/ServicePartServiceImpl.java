package com.example.demo.service.impl;

import com.example.demo.model.ServicePart;
import com.example.demo.repository.ServicePartRepository;
import com.example.demo.service.ServicePartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicePartServiceImpl implements ServicePartService {
    private final ServicePartRepository servicePartRepository;

    public ServicePartServiceImpl(ServicePartRepository servicePartRepository) {
        this.servicePartRepository = servicePartRepository;
    }

    @Override
    public ServicePart createPart(ServicePart part) {
        if (part.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        return servicePartRepository.save(part);
    }

    @Override
    public Optional<ServicePart> getPartById(Long id) {
        return servicePartRepository.findById(id);
    }

    @Override
    public List<ServicePart> getPartsForService(Long serviceEntryId) {
        return servicePartRepository.findByServiceEntryId(serviceEntryId);
    }
}