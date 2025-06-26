package com.ecomarket.ecomarket.assembler;

import com.ecomarket.ecomarket.model.Producto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductoAssembler {

    public ProductoModel toModel(@NonNull Producto producto) {
        ProductoModel model = new ProductoModel();

        model.setId(producto.getId());
        model.setCodigoBarra(producto.getCodigoBarra());
        model.setNombre(producto.getNombre());
        model.setDescripcion(producto.getDescripcion());
        model.setFechaVencimiento(producto.getFechaVencimiento());
        model.setCategoria(producto.getCategoria());
        model.setPrecio(producto.getPrecio());
        model.setStock(producto.getStock());

        return model;
    }

    public List<ProductoModel> toModelList(List<Producto> productos) {
        List<ProductoModel> list = new ArrayList<>();
        for (Producto p : productos) {
            list.add(toModel(p));
        }
        return list;
    }

    public static class ProductoModel {
        private Integer id;
        private String codigoBarra;
        private String nombre;
        private String descripcion;
        private java.sql.Date fechaVencimiento;
        private String categoria;
        private Integer precio;
        private Integer stock;

        // Getters y setters
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }

        public String getCodigoBarra() { return codigoBarra; }
        public void setCodigoBarra(String codigoBarra) { this.codigoBarra = codigoBarra; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

        public java.sql.Date getFechaVencimiento() { return fechaVencimiento; }
        public void setFechaVencimiento(java.sql.Date fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

        public String getCategoria() { return categoria; }
        public void setCategoria(String categoria) { this.categoria = categoria; }

        public Integer getPrecio() { return precio; }
        public void setPrecio(Integer precio) { this.precio = precio; }

        public Integer getStock() { return stock; }
        public void setStock(Integer stock) { this.stock = stock; }
    }
}
