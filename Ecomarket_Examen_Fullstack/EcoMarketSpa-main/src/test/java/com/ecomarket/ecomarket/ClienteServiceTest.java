package com.ecomarket.ecomarket;

import com.ecomarket.ecomarket.model.Cliente;
import com.ecomarket.ecomarket.repository.ClienteRepository;
import com.ecomarket.ecomarket.service.ClienteService;

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
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void testFindAll() {
        Cliente cliente1 = new Cliente();
        cliente1.setId(1);
        cliente1.setNombre_apellido("Juan Pérez");

        Cliente cliente2 = new Cliente();
        cliente2.setId(2);
        cliente2.setNombre_apellido("María Gómez");

        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

        List<Cliente> clientes = clienteService.findAll();

        assertEquals(2, clientes.size());
        verify(clienteRepository).findAll();
    }

    @Test
    void testFindById() {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNombre_apellido("Carlos Ruiz");

        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = clienteService.findById(1);

        assertTrue(resultado.isPresent());
        assertEquals("Carlos Ruiz", resultado.get().getNombre_apellido());
        verify(clienteRepository).findById(1);
    }

    @Test
    void testSave() {
        Cliente cliente = new Cliente();
        cliente.setNombre_apellido("Lucía Fernández");

        Cliente clienteGuardado = new Cliente();
        clienteGuardado.setId(1);
        clienteGuardado.setNombre_apellido(cliente.getNombre_apellido());

        when(clienteRepository.save(cliente)).thenReturn(clienteGuardado);

        Cliente resultado = clienteService.save(cliente);

        assertEquals("Lucía Fernández", resultado.getNombre_apellido());
        verify(clienteRepository).save(cliente);
    }

    @Test
    void testDeleteById() {
        Integer id = 1;
        doNothing().when(clienteRepository).deleteById(id);

        clienteService.deleteById(id);

        verify(clienteRepository).deleteById(id);
    }

    @Test
    void testUpdate() {
        Integer id = 1;

        Cliente clienteExistente = new Cliente();
        clienteExistente.setId(id);
        clienteExistente.setNombre_apellido("Ana López");
        clienteExistente.setCorreo("ana@correo.com");
        clienteExistente.setTelefono("123456789");
        clienteExistente.setDireccion("Calle 1");
        clienteExistente.setContraseña("1234");

        Cliente clienteActualizado = new Cliente();
        clienteActualizado.setNombre_apellido("Ana Torres");
        clienteActualizado.setCorreo("ana.torres@correo.com");
        clienteActualizado.setTelefono("987654321");
        clienteActualizado.setDireccion("Calle 2");
        clienteActualizado.setContraseña("abcd");

        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cliente resultado = clienteService.update(id, clienteActualizado);

        assertEquals("Ana Torres", resultado.getNombre_apellido());
        assertEquals("ana.torres@correo.com", resultado.getCorreo());
        assertEquals("987654321", resultado.getTelefono());
        assertEquals("Calle 2", resultado.getDireccion());
        assertEquals("abcd", resultado.getContraseña());
        verify(clienteRepository).save(clienteExistente);
    }

    @Test
    void testUpdate_ClienteNoEncontrado() {
        Integer id = 99;
        Cliente clienteActualizado = new Cliente();

        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.update(id, clienteActualizado);
        });

        assertEquals("Cliente no encontrado con id: 99", exception.getMessage());
        verify(clienteRepository).findById(id);
        verify(clienteRepository, never()).save(any());
    }
}