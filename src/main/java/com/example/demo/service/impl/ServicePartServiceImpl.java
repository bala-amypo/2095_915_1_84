package com.example.demo.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.model.ServicePart;
import com.example.demo.repository.ServicePartRepository;

@Service
public class ServicePartServiceImpl {

    private final ServicePartRepository repository;

    public ServicePartServiceImpl(ServicePartRepository repository) {
        this.repository = repository;
    }

    public ServicePart createPart(ServicePart part) {
        return repository.save(part);
    }
}
