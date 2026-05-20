package com.example.mgsc.unitarios.service;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.EstadoSolicitud;
import com.example.mgsc.dominio.Solicitud;
import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.dominio.TipoCliente;
import com.example.mgsc.infrastucture.interfaces.SolicitudRepositoryPort;
import com.example.mgsc.service.SolicitudService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitudServiceTest {

    @Mock
    private SolicitudRepositoryPort repositorio;

    @InjectMocks
    private SolicitudService servicio;

    // ── asignarTecnico ──────────────────────────────────────────────────────

    @Test
    void cuandoAsignaTecnicoActivoDebeGuardarSolicitudActualizada() {
        Cliente cliente = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        Solicitud solicitud = new Solicitud("Reparar fuga", cliente);
        Tecnico tecnico = new Tecnico(1, "Ana", "67890", "Electricidad", true);

        when(repositorio.buscarPorId(solicitud.getId())).thenReturn(Optional.of(solicitud));

        servicio.asignarTecnico(solicitud.getId(), tecnico);

        verify(repositorio).modificar(solicitud);
        assertEquals(EstadoSolicitud.EN_PROCESO, solicitud.getEstado());
    }

    @Test
    void asignarTecnicoInactivoDebeRetornarNegativo() {
        Cliente cliente = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        Solicitud solicitud = new Solicitud("Reparar fuga", cliente);
        Tecnico tecnicoInactivo = new Tecnico(2, "Pedro", "99999", "Redes", false);

        when(repositorio.buscarPorId(solicitud.getId())).thenReturn(Optional.of(solicitud));

        int resultado = servicio.asignarTecnico(solicitud.getId(), tecnicoInactivo);

        assertEquals(-1, resultado);
        verify(repositorio, never()).modificar(any());
    }

    @Test
    void asignarTecnicoEnSolicitudInexistenteDebeRetornarNegativo() {
        Tecnico tecnico = new Tecnico(1, "Ana", "67890", "Electricidad", true);
        when(repositorio.buscarPorId(99L)).thenReturn(Optional.empty());

        int resultado = servicio.asignarTecnico(99L, tecnico);

        assertEquals(-1, resultado);
    }

    // ── cambiarEstado ───────────────────────────────────────────────────────

    @Test
    void cuandoCambiarEstadoDebeGuardarSolicitudActualizada() {
        Cliente cliente = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        Solicitud solicitud = new Solicitud("Reparar fuga", cliente);

        when(repositorio.buscarPorId(solicitud.getId())).thenReturn(Optional.of(solicitud));

        servicio.cambiarEstado(solicitud.getId(), EstadoSolicitud.CERRADA);

        verify(repositorio).modificar(solicitud);
        assertEquals(EstadoSolicitud.CERRADA, solicitud.getEstado());
    }

    // ── cerrarSolicitud ─────────────────────────────────────────────────────

    @Test
    void cerrarSolicitudEnProcesoDebeActualizarEstadoYFecha() {
        Cliente cliente = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        Solicitud solicitud = new Solicitud("Reparar", cliente);
        solicitud.setEstado(EstadoSolicitud.EN_PROCESO);

        when(repositorio.buscarPorId(solicitud.getId())).thenReturn(Optional.of(solicitud));

        Integer resultado = servicio.cerrarSolicitud(solicitud.getId());

        verify(repositorio).modificar(solicitud);
        assertEquals(EstadoSolicitud.CERRADA, solicitud.getEstado());
        assertNotNull(solicitud.getFechaCierre());
        assertEquals(0, resultado);
    }

    @Test
    void cerrarSolicitudNoEnProcesoDebeRetornarNegativo() {
        Cliente cliente = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        Solicitud solicitud = new Solicitud("Reparar", cliente);
        // estado inicial = PENDIENTE, no EN_PROCESO

        when(repositorio.buscarPorId(solicitud.getId())).thenReturn(Optional.of(solicitud));

        Integer resultado = servicio.cerrarSolicitud(solicitud.getId());

        assertEquals(-1, resultado);
        verify(repositorio, never()).modificar(any());
    }

    @Test
    void cerrarSolicitudInexistenteDebeRetornarNegativo() {
        when(repositorio.buscarPorId(99L)).thenReturn(Optional.empty());

        Integer resultado = servicio.cerrarSolicitud(99L);

        assertEquals(-1, resultado);
    }
}
