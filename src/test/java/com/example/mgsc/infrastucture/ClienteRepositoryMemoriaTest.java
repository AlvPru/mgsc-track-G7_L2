package com.example.mgsc.infrastucture;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.TipoCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClienteRepositoryMemoriaTest {

    private ClienteRepositoryMemoria repository;

    @BeforeEach
    void setUp() {
        repository = ClienteRepositoryMemoria.getInstance();
        repository.limpiar();
    }

    @Test
    void guardarYBuscarPorIdDebeRetornarClienteCorrecto() {
        Cliente cliente = new Cliente(1, "Juan", "juan@email.com", TipoCliente.PREMIUM);

        repository.guardar(cliente);

        Optional<Cliente> resultado = repository.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertSame(cliente, resultado.get());
    }

    @Test
    void listarDebeRetornarCopiaIndependiente() {
        Cliente cliente = new Cliente(2, "Ana", "ana@email.com", TipoCliente.STANDARD);

        repository.guardar(cliente);

        List<Cliente> lista = repository.listar();
        assertEquals(1, lista.size());

        lista.clear();
        assertEquals(1, repository.listar().size());
    }
}
