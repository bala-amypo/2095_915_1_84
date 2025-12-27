package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Garage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String garageName;

    private String address;
    private String contactNumber;
    private Boolean active = true;

    // getters/setters
}
