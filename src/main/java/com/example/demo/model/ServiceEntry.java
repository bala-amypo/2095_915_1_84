package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "service_entries")
public class ServiceEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "odometer_reading", nullable = false)
    private Integer odometerReading;

    @Column(name = "service_type", nullable = false)
    private String serviceType;

    @Column(name = "service_date", nullable = false)
    private LocalDate serviceDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garage_id", nullable = false)
    private Garage garage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    // ----------- Constructors -----------

    public ServiceEntry() {
    }

    public ServiceEntry(Integer odometerReading,
                        String serviceType,
                        LocalDate serviceDate,
                        Garage garage,
                        Vehicle vehicle) {
        this.odometerReading = odometerReading;
        this.serviceType = serviceType;
        this.serviceDate = serviceDate;
        this.garage = garage;
        this.vehicle = vehicle;
    }

    // ----------- Getters & Setters -----------

    public Long getId() {
        return id;
    }

    public Integer getOdometerReading() {
        return odometerReading;
    }

    public void setOdometerReading(Integer odometerReading) {
        this.odometerReading = odometerReading;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
