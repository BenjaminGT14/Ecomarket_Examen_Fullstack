package com.ecomarket.ecomarket.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomarket.ecomarket.model.Cliente;
import com.ecomarket.ecomarket.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findById(Integer id) {
        return clienteRepository.findById(id);
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void deleteById(Integer id) {
        clienteRepository.deleteById(id);
    }

    public Cliente update(Integer id, Cliente clienteActualizado) {
        return clienteRepository.findById(id)
            .map(clienteExistente -> {
                clienteExistente.setNombre_apellido(clienteActualizado.getNombre_apellido());
                clienteExistente.setCorreo(clienteActualizado.getCorreo());
                clienteExistente.setTelefono(clienteActualizado.getTelefono());
                clienteExistente.setDireccion(clienteActualizado.getDireccion());
                clienteExistente.setContraseña(clienteActualizado.getContraseña());
                // No actualizamos fechaRegistro para mantener la fecha de creación original
                return clienteRepository.save(clienteExistente);
            })
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
    }
}

