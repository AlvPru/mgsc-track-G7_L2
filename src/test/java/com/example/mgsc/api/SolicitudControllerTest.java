package com.example.mgsc.api;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.Solicitud;
import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.dominio.TipoCliente;
import com.example.mgsc.service.SolicitudService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitudControllerTest {

    @Mock
    private SolicitudService solicitudService;

    @InjectMocks
    private SolicitudController controller;

    @Test
    void asignarTecnicoInactivoDebeRetornarMenosUno() {
        Tecnico tecnico = new Tecnico(1, "Ana", "Electricidad", false);

        int resultado = controller.asignarTecnico(1, tecnico);

        assertEquals(-1, resultado);
        verifyNoInteractions(solicitudService);
    }

    @Test
    void asignarTecnicoSolicitudNoEncontradaDebeRetornarMenosUno() {
        when(solicitudService.buscarPorId(100)).thenReturn(Optional.empty());
        Tecnico tecnico = new Tecnico(2, "Luis", "Plomeria", true);

        int resultado = controller.asignarTecnico(100, tecnico);

        assertEquals(-1, resultado);
        verify(solicitudService).buscarPorId(100);
    }

    @Test
    void cerrarSolicitudNoEncontradaDebeRetornarMenosUno() {
        when(solicitudService.buscarPorId(200)).thenReturn(Optional.empty());

        assertEquals(-1, controller.cerrarSolicitud(200));
    }

    @Test
    void cerrarSolicitudNoEnProcesoDebeRetornarMenosUno() {
        Cliente cliente = new Cliente(3, "Jose", "jose@email.com", TipoCliente.STANDARD);
        Solicitud solicitud = new Solicitud("Reparacion", cliente);
        when(solicitudService.buscarPorId(solicitud.getId())).thenReturn(Optional.of(solicitud));

        assertEquals(-1, controller.cerrarSolicitud(solicitud.getId()));
        verify(solicitudService).buscarPorId(solicitud.getId());
    }

    @Test
    void listarSolicitudesDebeDelegarAlServicio() {
        when(solicitudService.listar()).thenReturn(List.of());

        Object resultado = controller.listarSolicitudes();

        assertNotNull(resultado);
        verify(solicitudService).listar();
    }
}
