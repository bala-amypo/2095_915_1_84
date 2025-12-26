package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String vin;

    @NotBlank
    private String make;

    @NotBlank
    private String model;

    @NotNull
    private Long ownerId;

    @Builder.Default
    private Boolean active = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Vehicle(String vin, String make, String model, Long ownerId) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.ownerId = ownerId;
        this.active = true;
    }
}