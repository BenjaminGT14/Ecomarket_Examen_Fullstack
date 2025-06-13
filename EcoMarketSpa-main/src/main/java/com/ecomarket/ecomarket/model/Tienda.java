package com.ecomarket.ecomarket.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Tienda")
@Data
public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 150)
    private String direccion;

    @Column(nullable = false, length = 100)
    private String ciudad;

    @Column(nullable = false, length = 20)
    private String telefono;
}