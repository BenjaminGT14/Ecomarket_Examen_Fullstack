package com.ecomarket.ecomarket.controller;

import com.ecomarket.ecomarket.assembler.ClienteAssembler;
import com.ecomarket.ecomarket.assembler.ClienteAssembler.ClienteModel;
import com.ecomarket.ecomarket.model.Cliente;
import com.ecomarket.ecomarket.service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteAssembler clienteAssembler;

    // Obtener todos los clientes (devuelve lista de ClienteModel)
    @GetMapping
    public List<ClienteModel> getAllClientes() {
        List<Cliente> clientes = clienteService.findAll();
        return clienteAssembler.toModelList(clientes);
    }

    // Obtener un cliente por ID (devuelve ClienteModel)
    @GetMapping("/{id}")
    public ResponseEntity<ClienteModel> getClienteById(@PathVariable Integer id) {
        return clienteService.findById(id)
                .map(clienteAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear un nuevo cliente (devuelve ClienteModel)
    @PostMapping
    public ResponseEntity<ClienteModel> createCliente(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteService.save(cliente);
        ClienteModel model = clienteAssembler.toModel(nuevoCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    // Actualizar un cliente existente (devuelve ClienteModel)
    @PutMapping("/{id}")
    public ResponseEntity<ClienteModel> updateCliente(@PathVariable Integer id, @RequestBody Cliente clienteActualizado) {
        try {
            Cliente cliente = clienteService.update(id, clienteActualizado);
            ClienteModel model = clienteAssembler.toModel(cliente);
            return ResponseEntity.ok(model);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un cliente (sin cuerpo)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer id) {
        clienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
