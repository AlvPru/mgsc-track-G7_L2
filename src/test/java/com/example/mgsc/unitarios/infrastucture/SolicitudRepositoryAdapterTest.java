package com.example.mgsc.unitarios.infrastucture;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.Solicitud;
import com.example.mgsc.dominio.TipoCliente;
import com.example.mgsc.dominio.entidades.SolicitudEntity;
import com.example.mgsc.infrastucture.SolicitudRepositoryAdapter;
import com.example.mgsc.infrastucture.SolicitudRepositoryJPA;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitudRepositoryAdapterTest {

    @Mock
    private SolicitudRepositoryJPA jpaRepo;

    @InjectMocks
    private SolicitudRepositoryAdapter adapter;

    private Cliente clienteStandard() {
        Cliente c = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        c.setId(1L);
        return c;
    }

    private Cliente clientePremium() {
        Cliente c = new Cliente("Ana", "67890", "ana@email.com", TipoCliente.PREMIUM);
        c.setId(2L);
        return c;
    }

    private SolicitudEntity entityConId(long id, Cliente cliente) {
        Solicitud s = new Solicitud("Desc", cliente);
        s.setId(id);
        return SolicitudEntity.fromDomain(s);
    }

    // ── guardar ─────────────────────────────────────────────────────────────

    @Test
    void guardarDebeGuardarEntityYActualizarId() {
        Solicitud solicitud = new Solicitud("Reparar", clienteStandard());
        // id = -1 (nuevo)

        SolicitudEntity savedEntity = entityConId(5L, clienteStandard());
        when(jpaRepo.save(any())).thenReturn(savedEntity);

        adapter.guardar(solicitud);

        verify(jpaRepo).save(any());
        assertEquals(5L, solicitud.getId());
    }

    // ── buscarPorId ──────────────────────────────────────────────────────────

    @Test
    void buscarPorIdDebeRetornarSolicitudExistente() {
        SolicitudEntity entity = entityConId(1L, clienteStandard());
        when(jpaRepo.findById(1L)).thenReturn(Optional.of(entity));

        Optional<Solicitud> resultado = adapter.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Desc", resultado.get().getDescripcion());
    }

    @Test
    void buscarPorIdDebeRetornarVacioSiNoExiste() {
        when(jpaRepo.findById(99L)).thenReturn(Optional.empty());

        Optional<Solicitud> resultado = adapter.buscarPorId(99L);

        assertFalse(resultado.isPresent());
    }

    // ── listar ───────────────────────────────────────────────────────────────

    @Test
    void listarDebeRetornarTodasLasSolicitudes() {
        SolicitudEntity entity = entityConId(1L, clienteStandard());
        when(jpaRepo.findAll()).thenReturn(List.of(entity));

        List<Solicitud> resultado = adapter.listar();

        assertEquals(1, resultado.size());
        assertEquals("Desc", resultado.get(0).getDescripcion());
    }

    // ── modificar ────────────────────────────────────────────────────────────

    @Test
    void modificarConIdNegativoDebeRetornarNegativo() {
        Solicitud solicitud = new Solicitud("Desc", clienteStandard());
        // id = -1 por defecto

        int resultado = adapter.modificar(solicitud);

        assertEquals(-1, resultado);
        verify(jpaRepo, never()).save(any());
    }

    @Test
    void modificarConSolicitudInexistenteDebeRetornarNegativo() {
        Solicitud solicitud = new Solicitud("Desc", clienteStandard());
        solicitud.setId(5L);
        when(jpaRepo.findById(5L)).thenReturn(Optional.empty());

        int resultado = adapter.modificar(solicitud);

        assertEquals(-1, resultado);
        verify(jpaRepo, never()).save(any());
    }

    @Test
    void modificarSolicitudExistenteDebeGuardarYRetornarCero() {
        Solicitud solicitud = new Solicitud("Desc", clienteStandard());
        solicitud.setId(5L);
        SolicitudEntity entity = entityConId(5L, clienteStandard());
        when(jpaRepo.findById(5L)).thenReturn(Optional.of(entity));
        when(jpaRepo.save(any())).thenReturn(entity);

        int resultado = adapter.modificar(solicitud);

        assertEquals(0, resultado);
        verify(jpaRepo).save(any());
    }

    // ── getProximaSolicitud ──────────────────────────────────────────────────

    @Test
    void getProximaSolicitudConPremiumDebeRetornarPremiumPrimero() {
        SolicitudEntity standard = entityConId(1L, clienteStandard());
        SolicitudEntity premium = entityConId(2L, clientePremium());
        when(jpaRepo.findAll(any(Sort.class))).thenReturn(List.of(standard, premium));

        Solicitud resultado = adapter.getProximaSolicitud();

        assertNotNull(resultado);
        assertEquals(TipoCliente.PREMIUM, resultado.getClienteAsignado().getTipo());
    }

    @Test
    void getProximaSolicitudSinPremiumDebeRetornarPrimero() {
        SolicitudEntity standard1 = entityConId(1L, clienteStandard());
        SolicitudEntity standard2 = entityConId(3L, clienteStandard());
        when(jpaRepo.findAll(any(Sort.class))).thenReturn(List.of(standard1, standard2));

        Solicitud resultado = adapter.getProximaSolicitud();

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void getProximaSolicitudSinSolicitudesDebeRetornarNull() {
        when(jpaRepo.findAll(any(Sort.class))).thenReturn(List.of());

        Solicitud resultado = adapter.getProximaSolicitud();

        assertNull(resultado);
    }
}
