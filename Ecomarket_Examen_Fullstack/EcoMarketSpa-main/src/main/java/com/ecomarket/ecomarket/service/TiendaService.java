package com.ecomarket.ecomarket.service;

import com.ecomarket.ecomarket.model.Tienda;
import com.ecomarket.ecomarket.repository.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TiendaService {

    @Autowired
    private TiendaRepository tiendaRepository;

    public List<Tienda> findAll() {
        return tiendaRepository.findAll();
    }

    public Optional<Tienda> findById(Integer id) {
        return tiendaRepository.findById(id);
    }

    public Tienda save(Tienda tienda) {
        return tiendaRepository.save(tienda);
    }

    public void deleteById(Integer id) {
        tiendaRepository.deleteById(id);
    }

    public Tienda update(Integer id, Tienda tiendaActualizada) {
        return tiendaRepository.findById(id)
                .map(tienda -> {
                    tienda.setNombre(tiendaActualizada.getNombre());
                    tienda.setDireccion(tiendaActualizada.getDireccion());
                    tienda.setCiudad(tiendaActualizada.getCiudad());
                    tienda.setTelefono(tiendaActualizada.getTelefono());
                    return tiendaRepository.save(tienda);
                }).orElseThrow(() -> new RuntimeException("Tienda no encontrada con id: " + id));
    }
}