package com.ecomarket.ecomarket.controller;

import com.ecomarket.ecomarket.assembler.TiendaAssembler;
import com.ecomarket.ecomarket.assembler.TiendaAssembler.TiendaModel;
import com.ecomarket.ecomarket.model.Tienda;
import com.ecomarket.ecomarket.service.TiendaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tiendas")
public class TiendaController {

    @Autowired
    private TiendaService tiendaService;

    @Autowired
    private TiendaAssembler tiendaAssembler;

    @GetMapping
    public List<TiendaModel> getAllTiendas() {
        List<Tienda> tiendas = tiendaService.findAll();
        return tiendaAssembler.toModelList(tiendas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TiendaModel> getTiendaById(@PathVariable Integer id) {
        return tiendaService.findById(id)
                .map(tiendaAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TiendaModel> createTienda(@RequestBody Tienda tienda) {
        Tienda nueva = tiendaService.save(tienda);
        TiendaModel model = tiendaAssembler.toModel(nueva);
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TiendaModel> updateTienda(@PathVariable Integer id, @RequestBody Tienda tienda) {
        try {
            Tienda tiendaActualizada = tiendaService.update(id, tienda);
            TiendaModel model = tiendaAssembler.toModel(tiendaActualizada);
            return ResponseEntity.ok(model);
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
