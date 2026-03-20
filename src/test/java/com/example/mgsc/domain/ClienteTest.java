package com.example.mgsc.domain;

import com.example.mgsc.domain.TipoCliente;
import com.example.mgsc.domain.Cliente;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClienteTest {

    @BeforeEach
    void setUp() {
        // Ensure each test starts with a clean repository state
        Cliente.clearAll();
    }

    @Test
    void createCliente_shouldStoreClienteInRepository() {
        Cliente cliente = Cliente.create("Ana Pérez", "ana.perez@example.com", TipoCliente.STANDAR);

        assertNotNull(cliente.getId(), "Expected generated id to be non-null");
        assertEquals("Ana Pérez", cliente.getNombre());
        assertEquals("ana.perez@example.com", cliente.getEmail());
        assertEquals(TipoCliente.STANDAR, cliente.getTipoCliente());

        Optional<Cliente> fromRepo = Cliente.findById(cliente.getId());
        assertTrue(fromRepo.isPresent(), "Expected repository to contain created cliente");
        assertEquals(cliente, fromRepo.get());
    }

    @Test
    void updateCliente_shouldModifyBasicFields() {
        Cliente cliente = Cliente.create("Juan López", "juan.lopez@example.com", TipoCliente.PREMIUM);

        cliente.setNombre("Juan L.");
        cliente.setEmail("juan.l@example.com");
        cliente.setTipoCliente(TipoCliente.STANDAR);

        assertEquals("Juan L.", cliente.getNombre());
        assertEquals("juan.l@example.com", cliente.getEmail());
        assertEquals(TipoCliente.STANDAR, cliente.getTipoCliente());

        // Ensure repository still returns the same instance and updates are visible
        Optional<Cliente> fromRepo = Cliente.findById(cliente.getId());
        assertTrue(fromRepo.isPresent());
        assertSame(cliente, fromRepo.get());
    }

    @Test
    void findById_shouldReturnEmptyWhenNotFound() {
        Optional<Cliente> fromRepo = Cliente.findById(UUID.randomUUID());
        assertTrue(fromRepo.isEmpty());
    }

    @Test
    void findAll_shouldReturnAllStoredClients() {
        Cliente.create("Cliente 1", "c1@example.com", TipoCliente.STANDAR);
        Cliente.create("Cliente 2", "c2@example.com", TipoCliente.PREMIUM);

        List<Cliente> all = Cliente.findAll();

        assertEquals(2, all.size());
        assertTrue(all.stream().anyMatch(c -> "Cliente 1".equals(c.getNombre())));
        assertTrue(all.stream().anyMatch(c -> "Cliente 2".equals(c.getNombre())));
    }
}
