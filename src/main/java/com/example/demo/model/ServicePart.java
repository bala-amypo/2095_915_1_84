package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class ServicePart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ServiceEntry serviceEntry;

    private String partName;
    private String partNumber;
    private BigDecimal cost;
    private Integer quantity;

    // getters/setters
}
