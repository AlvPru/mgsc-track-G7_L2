package com.example.mgsc.unitarios.api;

import com.example.mgsc.api.DTOs.ClienteRequestDTO;
import com.example.mgsc.api.DTOs.ClienteResponseDTO;
import com.example.mgsc.dominio.TipoCliente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Tag;
@Tag("unitario")
class ClienteDTOTest {

    @Test
    void clienteRequestDTOGettersAndSetters() {
        ClienteRequestDTO request = new ClienteRequestDTO();

        request.setNombre("Pedro");
        request.setDni("12345");
        request.setEmail("pedro@example.com");
        request.setTipo(TipoCliente.PREMIUM);

        assertEquals("Pedro", request.getNombre());
        assertEquals("12345", request.getDni());
        assertEquals("pedro@example.com", request.getEmail());
        assertEquals(TipoCliente.PREMIUM, request.getTipo());
    }

    @Test
    void clienteResponseDTOGettersAndSetters() {
        ClienteResponseDTO response = new ClienteResponseDTO();

        response.setId(20);
        response.setNombre("Lucía");
        response.setEmail("lucia@example.com");
        response.setTipo(TipoCliente.STANDARD);

        assertEquals(20, response.getId());
        assertEquals("Lucía", response.getNombre());
        assertEquals("lucia@example.com", response.getEmail());
        assertEquals(TipoCliente.STANDARD, response.getTipo());
    }
}
