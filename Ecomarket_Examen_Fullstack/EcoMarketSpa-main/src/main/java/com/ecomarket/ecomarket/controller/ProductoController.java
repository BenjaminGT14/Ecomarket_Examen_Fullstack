package com.ecomarket.ecomarket.controller;

import com.ecomarket.ecomarket.assembler.ProductoAssembler;
import com.ecomarket.ecomarket.model.Producto;
import com.ecomarket.ecomarket.assembler.ProductoAssembler.ProductoModel;
import com.ecomarket.ecomarket.service.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoAssembler productoAssembler;

    // Obtener todos los productos con HATEOAS
    @GetMapping
    public List<ProductoModel> getAllProductos() {
        List<Producto> productos = productoService.findAll();
        return productos.stream()
                .map(productoAssembler::toModel)
                .collect(Collectors.toList());
    }

    // Obtener un producto por ID con HATEOAS
    @GetMapping("/{id}")
    public ResponseEntity<ProductoModel> getProductoById(@PathVariable Integer id) {
        return productoService.findById(id)
                .map(productoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear un nuevo producto (devuelve modelo HATEOAS)
    @PostMapping
    public ResponseEntity<ProductoModel> createProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.save(producto);
        ProductoModel model = productoAssembler.toModel(nuevoProducto);
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    // Actualizar un producto existente (devuelve modelo HATEOAS)
    @PutMapping("/{id}")
    public ResponseEntity<ProductoModel> updateProducto(@PathVariable Integer id, @RequestBody Producto productoActualizado) {
        try {
            Producto producto = productoService.update(id, productoActualizado);
            ProductoModel model = productoAssembler.toModel(producto);
            return ResponseEntity.ok(model);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un producto (sin cuerpo)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Integer id) {
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
