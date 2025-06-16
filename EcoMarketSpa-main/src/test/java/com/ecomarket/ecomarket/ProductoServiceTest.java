package com.ecomarket.ecomarket;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecomarket.ecomarket.model.Producto;
import com.ecomarket.ecomarket.repository.ProductoRepository;
import com.ecomarket.ecomarket.service.ProductoService;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock 
    ProductoRepository ProductoRepository;

    @InjectMocks 
    ProductoService ProductoService;

    @Test
    void guardarProducto_() {
    Producto producto = new Producto();
        producto.setCodigoBarra("123ABC789WES12");
        producto.setNombre("Miel");
        producto.setDescripcion("Miel natural");
        producto.setFechaVencimiento(null);
        producto.setCategoria("Producto animal");
        producto.setPrecio(6000);
        producto.setStock(20);

    Producto productoGuardado = new Producto();
        productoGuardado.setId(1);
        productoGuardado.setCodigoBarra(producto.getCodigoBarra());
        productoGuardado.setNombre(producto.getNombre());
        productoGuardado.setDescripcion(producto.getDescripcion());
        productoGuardado.setFechaVencimiento(producto.getFechaVencimiento());
        productoGuardado.setCategoria(producto.getCategoria());
        productoGuardado.setPrecio(producto.getPrecio());
        productoGuardado.setStock(producto.getStock());

        when(ProductoRepository.save(producto)).thenReturn(productoGuardado);

        Producto resultado = ProductoService.save(producto);

        assertEquals("Miel", resultado.getNombre());
        assertEquals(6000, resultado.getPrecio());
        assertEquals(20, resultado.getStock());

        verify(ProductoRepository).save(producto);
    }

    @Test
    void EncontrarPorID() {
         Integer id = 1;
        Producto producto = new Producto();
        producto.setId(id);
        producto.setNombre("miel");

        when(ProductoRepository.findById(id)).thenReturn(Optional.of(producto));

        Optional<Producto> resultado = ProductoService.findById(id);

        assertTrue(resultado.isPresent());
        assertEquals("miel", resultado.get().getNombre());
        verify(ProductoRepository).findById(id);
    }

        @Test
    void EliminarProducto() {
        Integer id = 1;

        doNothing().when(ProductoRepository).deleteById(id);

        ProductoService.deleteById(id);

        verify(ProductoRepository).deleteById(id);
    }
        @Test
     void ActualizarProducto() {
        Integer id = 1;

        Producto productoExistente = new Producto();
        productoExistente.setCodigoBarra("123ABC789WES12");
        productoExistente.setNombre("Miel");
        productoExistente.setDescripcion("Miel natural");
        productoExistente.setFechaVencimiento(null);
        productoExistente.setCategoria("producto animal");
        productoExistente.setPrecio(6000);
        productoExistente.setStock(20);

        Producto productoActualizado = new Producto();
        productoActualizado.setCodigoBarra("222");
        productoActualizado.setNombre("Cepillo de bambú");
        productoActualizado.setDescripcion("cepillo de dientes hecho de bambú");
        productoActualizado.setFechaVencimiento(null);
        productoActualizado.setCategoria("Articulos de aseo");
        productoActualizado.setPrecio(7000);
        productoActualizado.setStock(50);

        when(ProductoRepository.findById(id)).thenReturn(Optional.of(productoExistente));
        when(ProductoRepository.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Producto resultado = ProductoService.update(id, productoActualizado);

        assertEquals("Cepillo de bambú", resultado.getNombre());
        assertEquals(7000, resultado.getPrecio());
}

@Test
    void update_deberiaLanzarExcepcionSiNoExiste() {
        Integer id = 99;
        Producto productoActualizado = new Producto();

        when(ProductoRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ProductoService.update(id, productoActualizado);
        });

        assertEquals("Producto no encontrado con id: " + id, exception.getMessage());
        verify(ProductoRepository).findById(id);
        verify(ProductoRepository, never()).save(any());
    }

}