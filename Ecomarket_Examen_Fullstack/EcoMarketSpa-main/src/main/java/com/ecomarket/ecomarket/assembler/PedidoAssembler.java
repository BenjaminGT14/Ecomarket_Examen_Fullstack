package com.ecomarket.ecomarket.assembler;

import com.ecomarket.ecomarket.controller.PedidoController;
import com.ecomarket.ecomarket.model.Pedido;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PedidoAssembler implements RepresentationModelAssembler<Pedido, PedidoAssembler.PedidoModel> {

    @Override
    public @NonNull PedidoModel toModel(@NonNull Pedido pedido) {
        PedidoModel model = new PedidoModel();

        model.setId(pedido.getId());
        model.setIdCliente(pedido.getIdCliente());
        model.setIdTienda(pedido.getIdTienda());
        model.setTotal(pedido.getTotal());
        model.setEstado(pedido.getEstado());
        model.setFechaPedido(pedido.getFechaPedido());

        // Agregar enlaces HATEOAS
        model.add(linkTo(methodOn(PedidoController.class).getPedidoById(pedido.getId())).withSelfRel());
        model.add(linkTo(methodOn(PedidoController.class).getAllPedidos()).withRel("pedidos"));
        model.add(linkTo(methodOn(PedidoController.class).updatePedido(pedido.getId(), pedido)).withRel("actualizar"));
        model.add(linkTo(methodOn(PedidoController.class).deletePedido(pedido.getId())).withRel("eliminar"));

        return model;
    }

    public List<PedidoModel> toModelList(List<Pedido> pedidos) {
        List<PedidoModel> list = new ArrayList<>();
        for (Pedido p : pedidos) {
            list.add(toModel(p));
        }
        return list;
    }

    public static class PedidoModel extends RepresentationModel<PedidoModel> {
        private Integer id;
        private Integer idCliente;
        private Integer idTienda;
        private Double total;
        private String estado;
        private java.sql.Date fechaPedido;

        // Getters y setters
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }

        public Integer getIdCliente() { return idCliente; }
        public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }

        public Integer getIdTienda() { return idTienda; }
        public void setIdTienda(Integer idTienda) { this.idTienda = idTienda; }

        public Double getTotal() { return total; }
        public void setTotal(Double total) { this.total = total; }

        public String getEstado() { return estado; }
        public void setEstado(String estado) { this.estado = estado; }

        public java.sql.Date getFechaPedido() { return fechaPedido; }
        public void setFechaPedido(java.sql.Date fechaPedido) { this.fechaPedido = fechaPedido; }
    }
}
