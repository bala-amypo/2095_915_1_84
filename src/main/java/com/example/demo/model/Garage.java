package com.example.demo.model;

public class Garage {
    private Long id;
    private String garageName;
    private String address;
    private String contectNumber;
    private boolean active;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getGarageName() {
        return garageName;
    }
    public void setGarageName(String garageName) {
        this.garageName = garageName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getContectNumber() {
        return contectNumber;
    }
    public void setContectNumber(String contectNumber) {
        this.contectNumber = contectNumber;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public Garage(Long id, String garageName, String address, String contectNumber, boolean active) {
        this.id = id;
        this.garageName = garageName;
        this.address = address;
        this.contectNumber = contectNumber;
        this.active = active;
    }
    
    
}
