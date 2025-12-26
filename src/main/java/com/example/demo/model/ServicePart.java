package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "service_parts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ServicePart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "service_entry_id")
    private ServiceEntry serviceEntry;

    @NotBlank
    private String partName;

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @Positive
    private BigDecimal cost;

    public ServicePart(Long serviceEntryId, String partName, Integer quantity, BigDecimal cost) {
        this.partName = partName;
        this.quantity = quantity;
        this.cost = cost;
    }

    public Long getServiceEntryId() {
        return serviceEntry != null ? serviceEntry.getId() : null;
    }

    public void setServiceEntryId(Long serviceEntryId) {
        // This is handled by the serviceEntry relationship
    }
}