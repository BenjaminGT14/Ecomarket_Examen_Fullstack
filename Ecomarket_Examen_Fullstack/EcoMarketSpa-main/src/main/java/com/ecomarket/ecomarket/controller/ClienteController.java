package com.ecomarket.ecomarket.controller;

import com.ecomarket.ecomarket.assembler.ClienteAssembler;
import com.ecomarket.ecomarket.assembler.ClienteAssembler.ClienteModel;
import com.ecomarket.ecomarket.model.Cliente;
import com.ecomarket.ecomarket.service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteAssembler clienteAssembler;

    // Obtener todos los clientes con enlaces
    @GetMapping
    public CollectionModel<EntityModel<ClienteModel>> getAllClientes() {
        List<Cliente> clientes = clienteService.findAll();
        List<EntityModel<ClienteModel>> modelos = clientes.stream()
                .map(clienteAssembler::toModel)
                .toList();

        return CollectionModel.of(modelos,
                linkTo(methodOn(ClienteController.class).getAllClientes()).withSelfRel());
    }

    // Obtener un cliente por ID con enlaces
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ClienteModel>> getClienteById(@PathVariable Integer id) {
        return clienteService.findById(id)
                .map(clienteAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear un nuevo cliente con enlaces
    @PostMapping
    public ResponseEntity<EntityModel<ClienteModel>> createCliente(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteService.save(cliente);
        EntityModel<ClienteModel> model = clienteAssembler.toModel(nuevoCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    // Actualizar cliente con enlaces
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ClienteModel>> updateCliente(@PathVariable Integer id, @RequestBody Cliente clienteActualizado) {
        try {
            Cliente cliente = clienteService.update(id, clienteActualizado);
            EntityModel<ClienteModel> model = clienteAssembler.toModel(cliente);
            return ResponseEntity.ok(model);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer id) {
        clienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
