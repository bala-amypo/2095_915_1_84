package com.example.demo.service;

import com.example.demo.model.ServicePart;

import java.util.List;
import java.util.Optional;

public interface ServicePartService {
    ServicePart createPart(ServicePart part);
    Optional<ServicePart> getPartById(Long id);
    List<ServicePart> getPartsForService(Long serviceEntryId);
}