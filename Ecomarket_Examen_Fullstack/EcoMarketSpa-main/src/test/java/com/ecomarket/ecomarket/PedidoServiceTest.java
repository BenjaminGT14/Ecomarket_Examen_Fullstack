package com.ecomarket.ecomarket;

import com.ecomarket.ecomarket.model.Pedido;
import com.ecomarket.ecomarket.repository.PedidoRepository;
import com.ecomarket.ecomarket.service.PedidoService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    PedidoRepository pedidoRepository;

    @InjectMocks
    PedidoService pedidoService;

    @Test
    void guardarPedido() {
        Pedido pedido = new Pedido();
        pedido.setIdCliente(1);
        pedido.setIdTienda(2);
        pedido.setTotal(5000.0);
        pedido.setEstado("pendiente");

        Pedido guardado = new Pedido();
        guardado.setId(1);
        guardado.setIdCliente(pedido.getIdCliente());
        guardado.setIdTienda(pedido.getIdTienda());
        guardado.setTotal(pedido.getTotal());
        guardado.setEstado(pedido.getEstado());

        when(pedidoRepository.save(pedido)).thenReturn(guardado);

        Pedido resultado = pedidoService.save(pedido);

        assertEquals(1, resultado.getId());
        assertEquals(5000.0, resultado.getTotal());
        assertEquals("pendiente", resultado.getEstado());

        verify(pedidoRepository).save(pedido);
    }

    @Test
    void encontrarPorId() {
        Integer id = 1;
        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setEstado("pendiente");

        when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));

        Optional<Pedido> resultado = pedidoService.findById(id);

        assertTrue(resultado.isPresent());
        assertEquals("pendiente", resultado.get().getEstado());

        verify(pedidoRepository).findById(id);
    }

    @Test
    void eliminarPedido() {
        Integer id = 1;

        doNothing().when(pedidoRepository).deleteById(id);

        pedidoService.deleteById(id);

        verify(pedidoRepository).deleteById(id);
    }

    @Test
    void actualizarPedido() {
        Integer id = 1;

        Pedido existente = new Pedido();
        existente.setIdCliente(1);
        existente.setIdTienda(1);
        existente.setTotal(1000.0);
        existente.setEstado("pendiente");

        Pedido actualizado = new Pedido();
        actualizado.setIdCliente(2);
        actualizado.setIdTienda(3);
        actualizado.setTotal(2000.0);
        actualizado.setEstado("completado");

        when(pedidoRepository.findById(id)).thenReturn(Optional.of(existente));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(inv -> inv.getArgument(0));

        Pedido resultado = pedidoService.update(id, actualizado);

        assertEquals(2, resultado.getIdCliente());
        assertEquals(3, resultado.getIdTienda());
        assertEquals(2000.0, resultado.getTotal());
        assertEquals("completado", resultado.getEstado());
    }

    @Test
    void actualizarPedido_noExiste() {
        Integer id = 99;
        Pedido actualizado = new Pedido();

        when(pedidoRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.update(id, actualizado);
        });

        assertEquals("Pedido no encontrado con id: " + id, exception.getMessage());
        verify(pedidoRepository).findById(id);
        verify(pedidoRepository, never()).save(any());
    }

    @Test
    void listarTodosLosPedidos() {
        List<Pedido> lista = Arrays.asList(new Pedido(), new Pedido());

        when(pedidoRepository.findAll()).thenReturn(lista);

        List<Pedido> resultado = pedidoService.findAll();

        assertEquals(2, resultado.size());
        verify(pedidoRepository).findAll();
    }
}
