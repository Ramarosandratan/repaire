package com.rina.repaire.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "computers")
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(name = "serial_number", unique = true, nullable = false)
    private String serialNumber;

    @OneToMany(mappedBy = "computer", cascade = CascadeType.ALL)
    private List<Component> components;

    @OneToMany(mappedBy = "computer")
    private List<Repair> repairs;

    public Long getId() {
        return id;
    }
}
