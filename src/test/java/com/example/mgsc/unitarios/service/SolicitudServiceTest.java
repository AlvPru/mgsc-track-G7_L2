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
        assertEquals(EstadoSolicitud.EN_PROCESO, solicitud.getEstado());
    }

    // --- Paso 6 del PDF: escenario negativo ---

       @Test
    void cuandoCambiarEstadoDebeGuardarSolicitudActualizada() {
        Cliente cliente = new Cliente(1, "Juan", "juan@email.com", TipoCliente.STANDARD);
        Solicitud solicitud = new Solicitud("Reparar fuga", cliente);

        when(repositorio.buscarPorId(solicitud.getId())).thenReturn(Optional.of(solicitud));

        servicio.cambiarEstado(solicitud.getId(), EstadoSolicitud.CERRADA);

        verify(repositorio).modificar(solicitud);
        assertEquals(EstadoSolicitud.CERRADA, solicitud.getEstado());
    }


}
