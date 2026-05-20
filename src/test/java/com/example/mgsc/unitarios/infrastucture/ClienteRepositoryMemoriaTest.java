package com.example.mgsc.unitarios.infrastucture;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.TipoCliente;
import com.example.mgsc.infrastucture.ClienteRepositoryMemoria;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClienteRepositoryMemoriaTest {

    @Test
    void guardarYListarCliente() {
        ClienteRepositoryMemoria repo = new ClienteRepositoryMemoria();
        Cliente cliente = new Cliente("Juan", "11111", "juan@email.com", TipoCliente.STANDARD);

        repo.guardar(cliente);
        List<Cliente> lista = repo.listar();

        assertEquals(1, lista.size());
        assertEquals("Juan", lista.get(0).getNombre());
    }

    @Test
    void buscarPorIdExistenteDevuelveCliente() {
        ClienteRepositoryMemoria repo = new ClienteRepositoryMemoria();
        Cliente cliente = new Cliente("Ana", "22222", "ana@email.com", TipoCliente.PREMIUM);
        cliente.setId(42);
        repo.guardar(cliente);

        Optional<Cliente> resultado = repo.buscarPorId(42);

        assertTrue(resultado.isPresent());
        assertEquals("Ana", resultado.get().getNombre());
    }

    @Test
    void buscarPorIdInexistenteDevuelveVacio() {
        ClienteRepositoryMemoria repo = new ClienteRepositoryMemoria();

        Optional<Cliente> resultado = repo.buscarPorId(999);

        assertFalse(resultado.isPresent());
    }

    @Test
    void existePorEmailDevuelveTrueCuandoExiste() {
        ClienteRepositoryMemoria repo = new ClienteRepositoryMemoria();
        repo.guardar(new Cliente("Juan", "11111", "juan@email.com", TipoCliente.STANDARD));

        assertTrue(repo.existePorEmail("juan@email.com"));
    }

    @Test
    void existePorEmailDevuelveFalseCuandoNoExiste() {
        ClienteRepositoryMemoria repo = new ClienteRepositoryMemoria();

        assertFalse(repo.existePorEmail("noexiste@email.com"));
    }
}
