package com.example.mgsc.api;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.TipoCliente;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController controlador;

    @Test
    void crearClienteDebeGuardarYRetornarCliente() {
        Cliente resultado = controlador.crearCliente(1, "Juan", "juan@email.com", "STANDARD");
        verify(clienteService).guardar(any(Cliente.class));
        assertNotNull(resultado);
    }

    @Test
    void buscarPorIdDebeRetornarClienteCorrecto() {
        Cliente cliente = new Cliente(1, "Juan", "juan@email.com", TipoCliente.STANDARD);
        when(clienteService.buscarPorId(1)).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = controlador.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals(cliente, resultado.get());
    }

    @Test
    void listarDebeRetornarTodosLosClientes() {
        List<Cliente> clientes = Arrays.asList(
            new Cliente(1, "Juan", "juan@email.com", TipoCliente.STANDARD),
            new Cliente(2, "Ana", "ana@email.com", TipoCliente.PREMIUM)
        );
        when(clienteService.listar()).thenReturn(clientes);

        List<Cliente> resultado = controlador.listar();

        assertEquals(2, resultado.size());
    }
}
