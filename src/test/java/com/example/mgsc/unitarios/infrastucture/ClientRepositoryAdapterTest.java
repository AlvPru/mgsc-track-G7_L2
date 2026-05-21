package com.example.mgsc.unitarios.infrastucture;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.TipoCliente;
import com.example.mgsc.dominio.entidades.ClienteEntity;
import com.example.mgsc.infrastucture.ClientRepositoryAdapter;
import com.example.mgsc.infrastucture.ClientRepositoryJPA;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientRepositoryAdapterTest {

    @Mock
    private ClientRepositoryJPA jpaRepo;

    @InjectMocks
    private ClientRepositoryAdapter adapter;

    private Cliente clienteBase() {
        Cliente c = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        c.setId(1L);
        return c;
    }

    @Test
    void guardarDebeGuardarEntityYActualizarId() {
        Cliente cliente = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        // id = -1 (nuevo)

        Cliente clienteConId = clienteBase(); // id = 1
        ClienteEntity savedEntity = ClienteEntity.fromDomain(clienteConId);
        when(jpaRepo.save(any())).thenReturn(savedEntity);

        adapter.guardar(cliente);

        verify(jpaRepo).save(any());
        assertEquals(1L, cliente.getId());
    }

    @Test
    void buscarPorIdDebeRetornarClienteExistente() {
        Cliente clienteConId = clienteBase();
        ClienteEntity entity = ClienteEntity.fromDomain(clienteConId);
        when(jpaRepo.findById(1L)).thenReturn(Optional.of(entity));

        Optional<Cliente> resultado = adapter.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
    }

    @Test
    void buscarPorIdDebeRetornarVacioSiNoExiste() {
        when(jpaRepo.findById(99L)).thenReturn(Optional.empty());

        Optional<Cliente> resultado = adapter.buscarPorId(99L);

        assertFalse(resultado.isPresent());
    }

    @Test
    void listarDebeRetornarTodosLosClientes() {
        ClienteEntity entity = ClienteEntity.fromDomain(clienteBase());
        when(jpaRepo.findAll()).thenReturn(List.of(entity));

        List<Cliente> resultado = adapter.listar();

        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
    }

    @Test
    void existeConDniRegistradoDebeRetornarVerdadero() {
        when(jpaRepo.existsByDni("12345")).thenReturn(true);

        assertTrue(adapter.existe("12345"));
    }

    @Test
    void existeConDniNoRegistradoDebeRetornarFalso() {
        when(jpaRepo.existsByDni("99999")).thenReturn(false);

        assertFalse(adapter.existe("99999"));
    }

    @Test
    void existePorEmailRegistradoDebeRetornarVerdadero() {
        when(jpaRepo.existsByEmail("juan@email.com")).thenReturn(true);

        assertTrue(adapter.existePorEmail("juan@email.com"));
    }

    @Test
    void existePorEmailNoRegistradoDebeRetornarFalso() {
        when(jpaRepo.existsByEmail("otro@email.com")).thenReturn(false);

        assertFalse(adapter.existePorEmail("otro@email.com"));
    }

    @Test
    void buscarPorDniDebeRetornarClienteExistente() {
        ClienteEntity entity = ClienteEntity.fromDomain(clienteBase());
        when(jpaRepo.findByDni("12345")).thenReturn(Optional.of(entity));

        Optional<Cliente> resultado = adapter.buscarPorDni("12345");

        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
    }

    @Test
    void buscarPorDniDebeRetornarVacioSiNoExiste() {
        when(jpaRepo.findByDni("99999")).thenReturn(Optional.empty());

        Optional<Cliente> resultado = adapter.buscarPorDni("99999");

        assertFalse(resultado.isPresent());
    }
}
