package com.example.demo.repository;

import com.example.demo.model.ServicePart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicePartRepository extends JpaRepository<ServicePart, Long> {
}
