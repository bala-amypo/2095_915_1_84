package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class VerificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private ServiceEntry serviceEntry;

    private LocalDateTime verifiedAt;
    private Boolean verifiedBySystem = true;
    private String notes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ServiceEntry getServiceEntry() { return serviceEntry; }
    public void setServiceEntry(ServiceEntry serviceEntry) { this.serviceEntry = serviceEntry; }

    public LocalDateTime getVerifiedAt() { return verifiedAt; }
    public void setVerifiedAt(LocalDateTime verifiedAt) { this.verifiedAt = verifiedAt; }

    public Boolean getVerifiedBySystem() { return verifiedBySystem; }
    public void setVerifiedBySystem(Boolean verifiedBySystem) { this.verifiedBySystem = verifiedBySystem; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
