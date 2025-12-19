package com.example.demo.model;

import java.security.Timestamp;

public class Vehicle {

    private Long id;
    private String vin;
    private String make;
    private String model;
    private int year;
    private Long ownerId;
    private boolean active;
    private Timestamp createdAt;
    public Long getId() {
        return id;
    }
                    public void setId(Long id) {
                        this.id = id;
                    }
                    public String getVin() {
                        return vin;
                    }
                    public void setVin(String vin) {
                        this.vin = vin;
                    }
                    public String getMake() {
                        return make;
                    }
                    public void setMake(String make) {
                        this.make = make;
                    }
                    public String getModel() {
                        return model;
                    }
                    public void setModel(String model) {
                        this.model = model;
                    }
                    public int getYear() {
                        return year;
                    }
                    public void setYear(int year) {
                        this.year = year;
                    }
                    public Long getOwnerId() {
                        return ownerId;
                    }
                    public void setOwnerId(Long ownerId) {
                        this.ownerId = ownerId;
                    }
                    public boolean isActive() {
                        return active;
                    }
                    public void setActive(boolean active) {
                        this.active = active;
                    }
                    public Timestamp getCreatedAt() {
                        return createdAt;
                    }
                    public void setCreatedAt(Timestamp createdAt) {
                        this.createdAt = createdAt;
                    }
                    public Vehicle(Long id, String vin, String make, String model, int year, Long ownerId,
                            boolean active, Timestamp createdAt) {
                        this.id = id;
                        this.vin = vin;
                        this.make = make;
                        this.model = model;
                        this.year = year;
                        this.ownerId = ownerId;
                        this.active = active;
                        this.createdAt = createdAt;
                    }

                    
    
}
