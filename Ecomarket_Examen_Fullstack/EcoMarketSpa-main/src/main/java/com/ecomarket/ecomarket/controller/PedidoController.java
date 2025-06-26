package com.ecomarket.ecomarket.controller;

import com.ecomarket.ecomarket.assembler.PedidoAssembler;
import com.ecomarket.ecomarket.assembler.PedidoAssembler.PedidoModel;
import com.ecomarket.ecomarket.model.Pedido;
import com.ecomarket.ecomarket.service.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoAssembler pedidoAssembler;

    // GET todos los pedidos
    @GetMapping
    public List<PedidoModel> getAllPedidos() {
        List<Pedido> pedidos = pedidoService.findAll();
        return pedidoAssembler.toModelList(pedidos);
    }

    // GET un pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<PedidoModel> getPedidoById(@PathVariable Integer id) {
        return pedidoService.findById(id)
                .map(pedidoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST crear un nuevo pedido
    @PostMapping
    public ResponseEntity<PedidoModel> createPedido(@RequestBody Pedido pedido) {
        Pedido nuevoPedido = pedidoService.save(pedido);
        PedidoModel model = pedidoAssembler.toModel(nuevoPedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    // PUT actualizar pedido
    @PutMapping("/{id}")
    public ResponseEntity<PedidoModel> updatePedido(@PathVariable Integer id, @RequestBody Pedido pedidoActualizado) {
        try {
            Pedido actualizado = pedidoService.update(id, pedidoActualizado);
            PedidoModel model = pedidoAssembler.toModel(actualizado);
            return ResponseEntity.ok(model);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE eliminar pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Integer id) {
        pedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
