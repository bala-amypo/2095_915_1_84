package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_entries")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ServiceEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "garage_id")
    private Garage garage;

    @NotBlank
    private String serviceType;

    @NotNull
    @PastOrPresent
    private LocalDate serviceDate;

    @NotNull
    @Positive
    private Long odometerReading;

    @NotNull
    @Positive
    private BigDecimal cost;

    @Lob
    @Size(max = 2000)
    private String notes;

    @CreationTimestamp
    private LocalDateTime submittedAt;

    public ServiceEntry(Long vehicleId, Long garageId, String serviceType, LocalDate serviceDate, Long odometerReading, BigDecimal cost) {
        this.serviceType = serviceType;
        this.serviceDate = serviceDate;
        this.odometerReading = odometerReading;
        this.cost = cost;
    }

    public Long getVehicleId() {
        return vehicle != null ? vehicle.getId() : null;
    }

    public void setVehicleId(Long vehicleId) {
        // This is handled by the vehicle relationship
    }

    public Long getGarageId() {
        return garage != null ? garage.getId() : null;
    }

    public void setGarageId(Long garageId) {
        // This is handled by the garage relationship
    }
}