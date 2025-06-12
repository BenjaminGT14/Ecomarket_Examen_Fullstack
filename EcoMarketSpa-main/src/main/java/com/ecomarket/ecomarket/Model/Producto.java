package com.ecomarket.ecomarket.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name= "Producto")
@Data


public class Producto {
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique=true, length = 15, nullable=false)
    private String codigoBarra;

    @Column(nullable=false)
    private String nombre;

    @Column(nullable=false)
    private String descripcion;

    @Column(nullable=true)
    private Date fechaVencimiento;

    @Column(nullable=false)
    private String categoria;
    
    @Column(nullable=false)
    private Integer precio;
    
    @Column(nullable=false)
    private Integer stock;      
}
