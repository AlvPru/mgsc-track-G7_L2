package com.example.mgsc.unitarios.api;

import com.example.mgsc.api.ClienteController;
import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.TipoCliente;
import com.example.mgsc.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteController.class)
@Tag("pruebaWebMvc")
class ClienteControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteService clienteService;

    @Test
    void listarClientesDevuelveListaDTO() throws Exception {
        Cliente cliente1 = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        cliente1.setId(1);
        Cliente cliente2 = new Cliente("Ana", "67890", "ana@email.com", TipoCliente.PREMIUM);
        cliente2.setId(2);
        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);
        when(clienteService.listar()).thenReturn(clientes);

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                .andExpect(jsonPath("$[0].email").value("juan@email.com"))
                .andExpect(jsonPath("$[0].tipo").value("STANDARD"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nombre").value("Ana"))
                .andExpect(jsonPath("$[1].tipo").value("PREMIUM"));
    }

    @Test
    void obtenerClientePorIdDevuelveDTO() throws Exception {
        Cliente cliente = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        cliente.setId(1);
        when(clienteService.buscarPorIdOrThrow(1L)).thenReturn(cliente);

        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.email").value("juan@email.com"))
                .andExpect(jsonPath("$.tipo").value("STANDARD"));
    }

    @Test
    void crearClienteDevuelveDTO() throws Exception {
        Cliente cliente = new Cliente("Pedro", "54321", "pedro@email.com", TipoCliente.PREMIUM);
        cliente.setId(3);
        when(clienteService.crearCliente("Pedro", "54321", "pedro@email.com", TipoCliente.PREMIUM))
                .thenReturn(cliente);

        String requestBody = objectMapper.writeValueAsString(
                new com.example.mgsc.api.DTOs.ClienteRequestDTO("Pedro", "54321", "pedro@email.com", TipoCliente.PREMIUM));

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.nombre").value("Pedro"))
                .andExpect(jsonPath("$.email").value("pedro@email.com"))
                .andExpect(jsonPath("$.tipo").value("PREMIUM"));
    }
}
