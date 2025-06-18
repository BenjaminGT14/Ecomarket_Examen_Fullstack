package com.ecomarket.ecomarket;

import com.ecomarket.ecomarket.model.Tienda;
import com.ecomarket.ecomarket.repository.TiendaRepository;
import com.ecomarket.ecomarket.service.TiendaService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TiendaServiceTest {

    @Mock
    private TiendaRepository tiendaRepository;

    @InjectMocks
    private TiendaService tiendaService;

    @Test
    void testFindAll() {
        Tienda tienda1 = new Tienda();
        tienda1.setId(1);
        tienda1.setNombre("Tienda Central");

        Tienda tienda2 = new Tienda();
        tienda2.setId(2);
        tienda2.setNombre("Sucursal Norte");

        when(tiendaRepository.findAll()).thenReturn(Arrays.asList(tienda1, tienda2));

        List<Tienda> tiendas = tiendaService.findAll();

        assertEquals(2, tiendas.size());
        verify(tiendaRepository).findAll();
    }

    @Test
    void testFindById() {
        Tienda tienda = new Tienda();
        tienda.setId(1);
        tienda.setNombre("Tienda Central");

        when(tiendaRepository.findById(1)).thenReturn(Optional.of(tienda));

        Optional<Tienda> resultado = tiendaService.findById(1);

        assertTrue(resultado.isPresent());
        assertEquals("Tienda Central", resultado.get().getNombre());
        verify(tiendaRepository).findById(1);
    }

    @Test
    void testSave() {
        Tienda tienda = new Tienda();
        tienda.setNombre("Tienda Nueva");

        Tienda tiendaGuardada = new Tienda();
        tiendaGuardada.setId(1);
        tiendaGuardada.setNombre(tienda.getNombre());

        when(tiendaRepository.save(tienda)).thenReturn(tiendaGuardada);

        Tienda resultado = tiendaService.save(tienda);

        assertEquals("Tienda Nueva", resultado.getNombre());
        verify(tiendaRepository).save(tienda);
    }

    @Test
    void testDeleteById() {
        Integer id = 1;
        doNothing().when(tiendaRepository).deleteById(id);

        tiendaService.deleteById(id);

        verify(tiendaRepository).deleteById(id);
    }

    @Test
    void testUpdate() {
        Integer id = 1;

        Tienda tiendaExistente = new Tienda();
        tiendaExistente.setId(id);
        tiendaExistente.setNombre("Tienda Antigua");
        tiendaExistente.setDireccion("Calle 1");
        tiendaExistente.setCiudad("Ciudad Vieja");
        tiendaExistente.setTelefono("1111111");

        Tienda tiendaActualizada = new Tienda();
        tiendaActualizada.setNombre("Tienda Renovada");
        tiendaActualizada.setDireccion("Calle 2");
        tiendaActualizada.setCiudad("Ciudad Nueva");
        tiendaActualizada.setTelefono("2222222");

        when(tiendaRepository.findById(id)).thenReturn(Optional.of(tiendaExistente));
        when(tiendaRepository.save(any(Tienda.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Tienda resultado = tiendaService.update(id, tiendaActualizada);

        assertEquals("Tienda Renovada", resultado.getNombre());
        assertEquals("Calle 2", resultado.getDireccion());
        assertEquals("Ciudad Nueva", resultado.getCiudad());
        assertEquals("2222222", resultado.getTelefono());
        verify(tiendaRepository).save(tiendaExistente);
    }

    @Test
    void testUpdate_TiendaNoEncontrada() {
        Integer id = 99;
        Tienda tiendaActualizada = new Tienda();

        when(tiendaRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tiendaService.update(id, tiendaActualizada);
        });

        assertEquals("Tienda no encontrada con id: 99", exception.getMessage());
        verify(tiendaRepository).findById(id);
        verify(tiendaRepository, never()).save(any());
    }
}
