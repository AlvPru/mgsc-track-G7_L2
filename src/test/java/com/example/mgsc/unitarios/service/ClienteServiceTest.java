package com.example.mgsc.unitarios.service;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.TipoCliente;
import com.example.mgsc.infrastucture.interfaces.ClienteRepositoryPort;
import com.example.mgsc.service.ClienteService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepositoryPort repositorio;

    @InjectMocks
    private ClienteService servicio;

    @Test
    void guardarClienteDebeInvocarRepositorio() {
        Cliente cliente = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        servicio.guardar(cliente);
        verify(repositorio).guardar(cliente);
    }

    @Test
    void buscarPorIdDebeDelegarAlRepositorio() {
        Cliente cliente = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        cliente.setId(1);
        when(repositorio.buscarPorId(1)).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = servicio.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals(cliente, resultado.get());
    }

    @Test
    void listarDebeOrdenarPremiumPrimero() {
        Cliente standard = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        Cliente premium = new Cliente("Ana", "67890", "ana@email.com", TipoCliente.PREMIUM);
        when(repositorio.listar()).thenReturn(Arrays.asList(standard, premium));

        List<Cliente> resultado = servicio.listar();

        assertEquals(TipoCliente.PREMIUM, resultado.get(0).getTipo());
        assertEquals(TipoCliente.STANDARD, resultado.get(1).getTipo());
    }

    @Test
    void cuandoCrearClienteNuevoDebeGuardarYRetornar() {
        when(repositorio.existePorEmail("juan@email.com")).thenReturn(false);

        Cliente resultado = servicio.crearCliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);

        verify(repositorio).guardar(resultado);
        assertEquals("Juan", resultado.getNombre());
        assertEquals("juan@email.com", resultado.getEmail());
    }

    @Test
    void cuandoCrearClienteConEmailDuplicadoLanzaExcepcion() {
        when(repositorio.existePorEmail("juan@email.com")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
            servicio.crearCliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD));

        verify(repositorio, never()).guardar(any());
    }

    @Test
    void cuandoBuscarClienteInexistenteLanzaExcepcion() {
        when(repositorio.buscarPorId(99)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
            servicio.buscarPorIdOrThrow(99));
    }
}
