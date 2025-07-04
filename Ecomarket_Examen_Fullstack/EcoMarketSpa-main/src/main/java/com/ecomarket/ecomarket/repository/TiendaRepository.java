package com.ecomarket.ecomarket.repository;

import com.ecomarket.ecomarket.model.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Integer> {
}