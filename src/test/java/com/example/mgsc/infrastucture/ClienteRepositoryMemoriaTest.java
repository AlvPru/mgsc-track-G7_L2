package com.example.mgsc.infrastucture;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.TipoCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClienteRepositoryMemoriaTest {

    private ClienteRepositoryMemoria repositorio;

    @BeforeEach
    void setUp() {
        repositorio = ClienteRepositoryMemoria.getInstance();
        repositorio.clear();
    }

    @Test
    void guardarDebeAgregarClienteALaLista() {
        Cliente cliente = new Cliente(1, "Juan", "juan@email.com", TipoCliente.STANDARD);
        repositorio.guardar(cliente);

        Optional<Cliente> resultado = repositorio.buscarPorId(1);
        assertTrue(resultado.isPresent());
        assertEquals(cliente, resultado.get());
    }

    @Test
    void buscarPorIdDebeRetornarClienteSiExiste() {
        Cliente cliente = new Cliente(1, "Juan", "juan@email.com", TipoCliente.STANDARD);
        repositorio.guardar(cliente);

        Optional<Cliente> resultado = repositorio.buscarPorId(1);
        assertTrue(resultado.isPresent());
        assertEquals(cliente, resultado.get());
    }

    @Test
    void buscarPorIdDebeRetornarEmptySiNoExiste() {
        Optional<Cliente> resultado = repositorio.buscarPorId(1);
        assertFalse(resultado.isPresent());
    }

    @Test
    void listarDebeRetornarTodosLosClientes() {
        Cliente cliente1 = new Cliente(1, "Juan", "juan@email.com", TipoCliente.STANDARD);
        Cliente cliente2 = new Cliente(2, "Ana", "ana@email.com", TipoCliente.PREMIUM);
        repositorio.guardar(cliente1);
        repositorio.guardar(cliente2);

        List<Cliente> resultado = repositorio.listar();
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(cliente1));
        assertTrue(resultado.contains(cliente2));
    }

    @Test
    void listarDebeRetornarListaVaciaSiNoHayClientes() {
        List<Cliente> resultado = repositorio.listar();
        assertTrue(resultado.isEmpty());
    }
}