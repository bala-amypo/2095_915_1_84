package com.example.demo.service.impl;

import com.example.demo.repository.ServicePartRepository;
import org.springframework.stereotype.Service;

@Service
public class ServicePartServiceImpl {

    private final ServicePartRepository servicePartRepository;

    public ServicePartServiceImpl(ServicePartRepository servicePartRepository) {
        this.servicePartRepository = servicePartRepository;
    }

    // example method
    public void test() {
        servicePartRepository.findAll();
    }
}
