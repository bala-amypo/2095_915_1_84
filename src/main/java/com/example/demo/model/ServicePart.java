package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "service_parts")
public class ServicePart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ServiceEntry serviceEntry;

    private String partName;
    private Integer quantity;

    public Long getId() { 
        return id;
    }
    public void setId(Long id) { 
         this.id = id; 
    }

    public ServiceEntry getServiceEntry() { 
        return serviceEntry;
    }
    public void setServiceEntry(ServiceEntry serviceEntry) { 
        this.serviceEntry = serviceEntry;
    }

    public String getPartName() {
         return partName; 
    }
    public void setPartName(String partName) {
         this.partName = partName;
    }

    public Integer getQuantity() {
         return quantity;
    }
    public void setQuantity(Integer quantity) {
         this.quantity = quantity;
    }
}
