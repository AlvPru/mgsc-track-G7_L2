package com.example.mgsc.service;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.Solicitud;
import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.dominio.TipoCliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitudServiceTest {

    @Mock
    private SolicitudRepositoryPort repositorio;

    @InjectMocks
    private SolicitudService servicio;

    // --- Paso 3 del PDF: caso positivo de asignarTecnico ---

    @Test
    void cuandoAsignaTecnicoActivoDebeGuardarSolicitudActualizada() {
        Cliente cliente = new Cliente(1, "Juan", "juan@email.com", TipoCliente.STANDARD);
        Solicitud solicitud = new Solicitud("Reparar fuga", cliente);
        Tecnico tecnico = new Tecnico(1, "Ana", "Electricidad", true);

        when(repositorio.buscarPorId(solicitud.getId())).thenReturn(Optional.of(solicitud));

        servicio.asignarTecnico(solicitud.getId(), tecnico);

        verify(repositorio).modificar(solicitud);
        assertEquals("EN PROCESO", solicitud.getEstado());
    }

    // --- Paso 6 del PDF: escenario negativo ---

    @Test
    void cuandoSolicitudNoExisteAlAsignarLanzaExcepcion() {
        Tecnico tecnico = new Tecnico(1, "Ana", "Electricidad", true);
        when(repositorio.buscarPorId(99)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
            servicio.asignarTecnico(99, tecnico));

        verify(repositorio, never()).modificar(any());
    }

    @Test
    void cuandoCambiarEstadoDebeGuardarSolicitudActualizada() {
        Cliente cliente = new Cliente(1, "Juan", "juan@email.com", TipoCliente.STANDARD);
        Solicitud solicitud = new Solicitud("Reparar fuga", cliente);

        when(repositorio.buscarPorId(solicitud.getId())).thenReturn(Optional.of(solicitud));

        servicio.cambiarEstado(solicitud.getId(), "CERRADA");

        verify(repositorio).modificar(solicitud);
        assertEquals("CERRADA", solicitud.getEstado());
    }

    @Test
    void cuandoCambiarEstadoEnSolicitudNoExisteLanzaExcepcion() {
        when(repositorio.buscarPorId(99)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
            servicio.cambiarEstado(99, "CERRADA"));

        verify(repositorio, never()).modificar(any());
    }
}
