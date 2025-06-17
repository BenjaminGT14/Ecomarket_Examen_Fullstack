package com.ecomarket.ecomarket.service;

import com.ecomarket.ecomarket.model.Pedido;
import com.ecomarket.ecomarket.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> findById(Integer id) {
        return pedidoRepository.findById(id);
    }

    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void deleteById(Integer id) {
        pedidoRepository.deleteById(id);
    }

    public Pedido update(Integer id, Pedido pedidoActualizado) {
        return pedidoRepository.findById(id)
                .map(pedido -> {
                    pedido.setIdCliente(pedidoActualizado.getIdCliente());
                    pedido.setIdTienda(pedidoActualizado.getIdTienda());
                    pedido.setTotal(pedidoActualizado.getTotal());
                    pedido.setEstado(pedidoActualizado.getEstado());
                    // No actualizamos fechaPedido
                    return pedidoRepository.save(pedido);
                })
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));
    }
}