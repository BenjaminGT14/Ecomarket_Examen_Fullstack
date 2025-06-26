package com.ecomarket.ecomarket.assembler;

import com.ecomarket.ecomarket.model.Cliente;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClienteAssembler {

    // Convierte Cliente a ClienteModel (DTO simple)
    public ClienteModel toModel(Cliente cliente) {
        ClienteModel model = new ClienteModel();
        model.setId(cliente.getId());
        model.setNombre_apellido(cliente.getNombre_apellido());
        model.setCorreo(cliente.getCorreo());
        model.setTelefono(cliente.getTelefono());
        model.setDireccion(cliente.getDireccion());
        model.setContraseña(cliente.getContraseña());
        model.setFechaRegistro(cliente.getFechaRegistro());
        return model;
    }

    // Convierte lista de Clientes a lista de ClienteModels
    public List<ClienteModel> toModelList(List<Cliente> clientes) {
        List<ClienteModel> listaModel = new ArrayList<>();
        for (Cliente c : clientes) {
            listaModel.add(toModel(c));
        }
        return listaModel;
    }

    // DTO simple sin heredar nada
    public static class ClienteModel {
        private Integer id;
        private String nombre_apellido;
        private String correo;
        private String telefono;
        private String direccion;
        private String contraseña;
        private java.sql.Date fechaRegistro;

        // Getters y setters
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }

        public String getNombre_apellido() { return nombre_apellido; }
        public void setNombre_apellido(String nombre_apellido) { this.nombre_apellido = nombre_apellido; }

        public String getCorreo() { return correo; }
        public void setCorreo(String correo) { this.correo = correo; }

        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }

        public String getDireccion() { return direccion; }
        public void setDireccion(String direccion) { this.direccion = direccion; }

        public String getContraseña() { return contraseña; }
        public void setContraseña(String contraseña) { this.contraseña = contraseña; }

        public java.sql.Date getFechaRegistro() { return fechaRegistro; }
        public void setFechaRegistro(java.sql.Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    }
}
