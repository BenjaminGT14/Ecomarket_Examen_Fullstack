package com.ecomarket.ecomarket.controller;

import com.ecomarket.ecomarket.model.Tienda;
import com.ecomarket.ecomarket.service.TiendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tiendas")
public class TiendaController {

    @Autowired
    private TiendaService tiendaService;

    @GetMapping
    public List<Tienda> getAllTiendas() {
        return tiendaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tienda> getTiendaById(@PathVariable Integer id) {
        return tiendaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tienda> createTienda(@RequestBody Tienda tienda) {
        Tienda nueva = tiendaService.save(tienda);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tienda> updateTienda(@PathVariable Integer id, @RequestBody Tienda tienda) {
        try {
            return ResponseEntity.ok(tiendaService.update(id, tienda));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTienda(@PathVariable Integer id) {
        tiendaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}