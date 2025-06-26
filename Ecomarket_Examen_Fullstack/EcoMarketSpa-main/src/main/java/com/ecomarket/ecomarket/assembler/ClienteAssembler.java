package com.ecomarket.ecomarket.assembler;

import com.ecomarket.ecomarket.controller.ClienteController;
import com.ecomarket.ecomarket.model.Cliente;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ClienteAssembler {

    // Convierte Cliente a EntityModel<ClienteModel> con enlaces HATEOAS
    public EntityModel<ClienteModel> toModel(Cliente cliente) {
        ClienteModel model = new ClienteModel();
        model.setId(cliente.getId());
        model.setNombre_apellido(cliente.getNombre_apellido());
        model.setCorreo(cliente.getCorreo());
        model.setTelefono(cliente.getTelefono());
        model.setDireccion(cliente.getDireccion());
        model.setContraseña(cliente.getContraseña());
        model.setFechaRegistro(cliente.getFechaRegistro());

        return EntityModel.of(model,
                linkTo(methodOn(ClienteController.class).getClienteById(cliente.getId())).withSelfRel(),
                linkTo(methodOn(ClienteController.class).getAllClientes()).withRel("clientes"),
                linkTo(methodOn(ClienteController.class).updateCliente(cliente.getId(), cliente)).withRel("actualizar"),
                linkTo(methodOn(ClienteController.class).deleteCliente(cliente.getId())).withRel("eliminar")
        );
    }

    // Convierte lista de Clientes a lista de EntityModel<ClienteModel> con enlaces
    public List<EntityModel<ClienteModel>> toModelList(List<Cliente> clientes) {
        return clientes.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
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
