package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.sql.Timestamp;

@Entity
public class ServiceEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Vehicle vehicle;

    @ManyToOne
    private Garage garage;

    private String serviceType;
    private LocalDate serviceDate;
    private Integer odometerReading;
    private String description;

    private Timestamp recordedAt = new Timestamp(System.currentTimeMillis());

    // getters/setters
}
