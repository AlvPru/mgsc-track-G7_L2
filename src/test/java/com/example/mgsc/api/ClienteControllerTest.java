package com.example.mgsc.api;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.TipoCliente;
import com.example.mgsc.service.ClienteService;
import com.example.mgsc.infrastucture.ClienteRepositoryMemoria;
import com.example.mgsc.service.ClienteRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClienteControllerTest {

    private ClienteRepositoryPort repo;
    private ClienteService service;
    private ClienteController controlador;

    @BeforeEach
    void setUp() {
        repo = ClienteRepositoryMemoria.getInstance();
        ((ClienteRepositoryMemoria) repo).clear();
        service = new ClienteService(repo);
        controlador = new ClienteController(service);
    }

    
    @Test
    void buscarPorIdDebeRetornarClienteCorrecto() {
        Cliente cliente = new Cliente(1, "Juan", "juan@email.com", TipoCliente.STANDARD);
        controlador.registrarCliente(cliente);

        Optional<Cliente> resultado = controlador.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals(cliente, resultado.get());
    }

    @Test
    void listarDebeRetornarTodosLosClientes() {
        Cliente cliente1 = new Cliente(1, "Juan", "juan@email.com", TipoCliente.STANDARD);
        Cliente cliente2 = new Cliente(2, "Ana", "ana@email.com", TipoCliente.PREMIUM);
        controlador.registrarCliente(cliente1);
        controlador.registrarCliente(cliente2);

        List<Cliente> resultado = controlador.listar();

        assertEquals(2, resultado.size());
 
    }
}
