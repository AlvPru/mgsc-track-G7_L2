package com.example.mgsc.service;

import com.example.mgsc.dominio.Solicitud;
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
class SolicitudServiceTest {

    @Mock
    private SolicitudRepositoryPort repository;

    @InjectMocks
    private SolicitudService service;

    @Test
    void listarDebeDelegarAlRepositorio() {
        when(repository.listar()).thenReturn(List.of());

        List<Solicitud> resultado = service.listar();

        assertNotNull(resultado);
        verify(repository).listar();
    }

    @Test
    void getProximaSolicitudDebeDelegarAlRepositorio() {
        Solicitud solicitud = new Solicitud("Prueba", null);
        when(repository.getProximaSolicitud()).thenReturn(solicitud);

        Solicitud resultado = service.getProximaSolicitud();

        assertSame(solicitud, resultado);
    }

    @Test
    void modificarDebeDelegarAlRepositorio() {
        Solicitud solicitud = new Solicitud("Prueba", null);
        when(repository.modificar(solicitud)).thenReturn(0);

        int resultado = service.modificar(solicitud);

        assertEquals(0, resultado);
        verify(repository).modificar(solicitud);
    }
}
