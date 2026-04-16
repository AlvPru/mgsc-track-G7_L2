package com.example.mgsc.infrastucture;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.TipoCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClienteRepositoryMemoriaTest {

    @BeforeEach
    void resetSingleton() throws Exception {
        Field instance = ClienteRepositoryMemoria.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    void getInstanceDevuelveInstanciaExistente() {
        ClienteRepositoryMemoria primera = ClienteRepositoryMemoria.getInstance();
        ClienteRepositoryMemoria segunda = ClienteRepositoryMemoria.getInstance();
        assertSame(primera, segunda);
    }

    @Test
    void guardarYListarCliente() {
        ClienteRepositoryMemoria repo = ClienteRepositoryMemoria.getInstance();
        Cliente cliente = new Cliente(1, "Juan", "juan@email.com", TipoCliente.STANDARD);

        repo.guardar(cliente);
        List<Cliente> lista = repo.listar();

        assertEquals(1, lista.size());
        assertEquals("Juan", lista.get(0).getNombre());
    }

    @Test
    void buscarPorIdExistenteDevuelveCliente() {
        ClienteRepositoryMemoria repo = ClienteRepositoryMemoria.getInstance();
        Cliente cliente = new Cliente(42, "Ana", "ana@email.com", TipoCliente.PREMIUM);
        repo.guardar(cliente);

        Optional<Cliente> resultado = repo.buscarPorId(42);

        assertTrue(resultado.isPresent());
        assertEquals("Ana", resultado.get().getNombre());
    }

    @Test
    void buscarPorIdInexistenteDevuelveVacio() {
        ClienteRepositoryMemoria repo = ClienteRepositoryMemoria.getInstance();

        Optional<Cliente> resultado = repo.buscarPorId(999);

        assertFalse(resultado.isPresent());
    }
}
