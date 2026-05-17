package com.example.mgsc.unitarios.api;

import com.example.mgsc.api.ClienteController;
import com.example.mgsc.api.DTOs.ClienteRequestDTO;
import com.example.mgsc.api.DTOs.ClienteResponseDTO;
import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.TipoCliente;
import com.example.mgsc.service.ClienteService;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("unitario")
@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController controlador;

    @Test
    void crearClienteDebeDelegarAlServicioYRetornarDTO() {
        Cliente cliente = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        cliente.setId(1);
        when(clienteService.crearCliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD))
                .thenReturn(cliente);

        ClienteRequestDTO request = new ClienteRequestDTO("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        ResponseEntity<ClienteResponseDTO> response = controlador.crearCliente(request);
        ClienteResponseDTO resultado = response.getBody();

        verify(clienteService).crearCliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Juan", resultado.getNombre());
        assertEquals("juan@email.com", resultado.getEmail());
        assertEquals(TipoCliente.STANDARD, resultado.getTipo());
    }

    @Test
    void obtenerClienteDebeRetornarDTOCorrecto() {
        Cliente cliente = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        cliente.setId(1);
        when(clienteService.buscarPorIdOrThrow(1)).thenReturn(cliente);

        ClienteResponseDTO resultado = controlador.obtenerCliente(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Juan", resultado.getNombre());
        assertEquals("juan@email.com", resultado.getEmail());
        assertEquals(TipoCliente.STANDARD, resultado.getTipo());
    }

    @Test
    void listarClientesDebeRetornarListaDeDTOs() {
        Cliente cliente1 = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        cliente1.setId(1);
        Cliente cliente2 = new Cliente("Ana", "67890", "ana@email.com", TipoCliente.PREMIUM);
        cliente2.setId(2);
        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);
        when(clienteService.listar()).thenReturn(clientes);

        List<ClienteResponseDTO> resultado = controlador.listarClientes();

        assertEquals(2, resultado.size());
        assertEquals(1, resultado.get(0).getId());
        assertEquals("Juan", resultado.get(0).getNombre());
        assertEquals(TipoCliente.STANDARD, resultado.get(0).getTipo());
        assertEquals(2, resultado.get(1).getId());
        assertEquals("Ana", resultado.get(1).getNombre());
        assertEquals(TipoCliente.PREMIUM, resultado.get(1).getTipo());
    }
}
