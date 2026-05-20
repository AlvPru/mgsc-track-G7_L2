package com.example.mgsc.unitarios.service;

import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.infrastucture.interfaces.TecnicoRepositoryPort;
import com.example.mgsc.service.TecnicoService;

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
class TecnicoServiceTest {

    @Mock
    private TecnicoRepositoryPort repositorio;

    @InjectMocks
    private TecnicoService servicio;

    // ── guardar y buscarActivo ──────────────────────────────────────────────

    @Test
    void deberiaGuardarUnTecnico() {
        Tecnico tecnico = new Tecnico(1, "Juan", "12345", "Redes", true);

        servicio.guardar(tecnico);

        verify(repositorio).guardar(tecnico);
    }

    @Test
    void deberiaEncontrarTecnicoActivo() {
        Tecnico tecnicoActivo = new Tecnico(1, "Ana", "67890", "Software", true);
        when(repositorio.buscarActivo()).thenReturn(List.of(tecnicoActivo));

        List<Tecnico> activos = servicio.buscarActivo();

        assertEquals(1, activos.size());
        assertTrue(activos.get(0).isActivo());
    }

    @Test
    void noDeberiaEncontrarTecnicoInactivo() {
        when(repositorio.buscarActivo()).thenReturn(List.of());

        List<Tecnico> activos = servicio.buscarActivo();

        assertEquals(0, activos.size());
    }

    // ── crearTecnico ────────────────────────────────────────────────────────

    @Test
    void crearTecnicoDebeGuardarYRetornar() {
        Tecnico tecnicoGuardado = new Tecnico(1, "Ana", "67890", "Software", true);
        when(repositorio.buscarPorDni("67890")).thenReturn(Optional.empty());
        when(repositorio.guardar(any())).thenReturn(tecnicoGuardado);

        Tecnico resultado = servicio.crearTecnico("Ana", "67890", "Software", true);

        verify(repositorio).guardar(any());
        assertEquals("Ana", resultado.getNombre());
    }

    @Test
    void crearTecnicoConDniDuplicadoLanzaExcepcion() {
        Tecnico existente = new Tecnico(1, "Juan", "12345", "Redes", true);
        when(repositorio.buscarPorDni("12345")).thenReturn(Optional.of(existente));

        assertThrows(IllegalArgumentException.class, () ->
            servicio.crearTecnico("Pedro", "12345", "Plomería", true));

        verify(repositorio, never()).guardar(any());
    }

    // ── buscarPorId ─────────────────────────────────────────────────────────

    @Test
    void buscarPorIdDebeDelegarAlRepositorio() {
        Tecnico tecnico = new Tecnico(1, "Juan", "12345", "Redes", true);
        when(repositorio.buscarPorId(1L)).thenReturn(Optional.of(tecnico));

        Optional<Tecnico> resultado = servicio.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
    }

    @Test
    void buscarPorIdOrThrowConIdInexistenteLanzaExcepcion() {
        when(repositorio.buscarPorId(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
            servicio.buscarPorIdOrThrow(99L));
    }
}
