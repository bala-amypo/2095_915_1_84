package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "verification_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VerificationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "service_entry_id")
    private ServiceEntry serviceEntry;

    @CreationTimestamp
    private LocalDateTime verifiedAt;

    @NotBlank
    private String verifiedBy;

    public VerificationLog(Long serviceEntryId, String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public Long getServiceEntryId() {
        return serviceEntry != null ? serviceEntry.getId() : null;
    }

    public void setServiceEntryId(Long serviceEntryId) {
        // This is handled by the serviceEntry relationship
    }
}