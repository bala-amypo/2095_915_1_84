package com.example.demo.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "verification_logs")
public class VerificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "service_entry_id", nullable = false)
    private ServiceEntry serviceEntry;

    @Column(nullable = false, updatable = false)
    private Timestamp verifiedAt;

    @Column(nullable = false)
    private Boolean verifiedBySystem = true;

    private String notes;

    @PrePersist
    protected void onCreate() {
        this.verifiedAt = new Timestamp(System.currentTimeMillis());
        if (this.verifiedBySystem == null) {
            this.verifiedBySystem = true;
        }
    }

    // -------- getters & setters --------

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

    public Timestamp getVerifiedAt() {
        return verifiedAt;
    }

    public Boolean getVerifiedBySystem() {
        return verifiedBySystem;
    }

    public void setVerifiedBySystem(Boolean verifiedBySystem) {
        this.verifiedBySystem = verifiedBySystem;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
