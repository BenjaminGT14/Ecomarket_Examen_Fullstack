package com.ecomarket.ecomarket.assembler;

import com.ecomarket.ecomarket.model.Tienda;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TiendaAssembler {

    public TiendaModel toModel(@NonNull Tienda tienda) {
        TiendaModel model = new TiendaModel();

        model.setId(tienda.getId());
        model.setNombre(tienda.getNombre());
        model.setDireccion(tienda.getDireccion());
        model.setCiudad(tienda.getCiudad());
        model.setTelefono(tienda.getTelefono());

        return model;
    }

    public List<TiendaModel> toModelList(List<Tienda> tiendas) {
        List<TiendaModel> list = new ArrayList<>();
        for (Tienda t : tiendas) {
            list.add(toModel(t));
        }
        return list;
    }

    public static class TiendaModel {
        private Integer id;
        private String nombre;
        private String direccion;
        private String ciudad;
        private String telefono;

        // Getters y setters
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getDireccion() { return direccion; }
        public void setDireccion(String direccion) { this.direccion = direccion; }

        public String getCiudad() { return ciudad; }
        public void setCiudad(String ciudad) { this.ciudad = ciudad; }

        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }
    }
}
