package com.ecomarket.ecomarket.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "Pedido")
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer idCliente;

    @Column(nullable = false)
    private Integer idTienda;

    @Column(nullable = false)
    private Double total;

    @Column(nullable = false, length = 50)
    private String estado; // ej: PENDIENTE, PAGADO, ENVIADO

    @Column(nullable = false)
    private Date fechaPedido;

    @PrePersist
    protected void onCreate() {
        this.fechaPedido = Date.valueOf(LocalDate.now());
    }
}