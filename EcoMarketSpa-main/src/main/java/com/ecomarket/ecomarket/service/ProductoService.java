package com.ecomarket.ecomarket.service;

import java.util.Optional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecomarket.ecomarket.model.Producto;
import com.ecomarket.ecomarket.repository.ProductoRepository;


@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Optional<Producto> findById(Integer id) {
        return productoRepository.findById(id);
    }

    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    public void deleteById(Integer id) {
        productoRepository.deleteById(id);
    }
    public Producto update(Integer id, Producto productoActualizado) {
    return productoRepository.findById(id)
        .map(productoExistente -> {
            productoExistente.setCodigoBarra(productoActualizado.getCodigoBarra());
            productoExistente.setNombre(productoActualizado.getNombre());
            productoExistente.setDescripcion(productoActualizado.getDescripcion());
            productoExistente.setFechaVencimiento(productoActualizado.getFechaVencimiento());
            productoExistente.setCategoria(productoActualizado.getCategoria());
            productoExistente.setPrecio(productoActualizado.getPrecio());
            productoExistente.setStock(productoActualizado.getStock());
            return productoRepository.save(productoExistente);
        })
        .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
}    
}
