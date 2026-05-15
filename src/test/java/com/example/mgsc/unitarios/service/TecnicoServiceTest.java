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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TecnicoServiceTest {

    @Mock
    private TecnicoRepositoryPort repositorio;

    @InjectMocks
    private TecnicoService servicio;

    @Test
    void deberiaGuardarUnTecnico() {
        Tecnico tecnico = new Tecnico(1, "Juan", "12345", "Redes", true);

        servicio.guardar(tecnico);

        verify(repositorio).guardar(tecnico);
    }

    // 1. CASO ACERTADO (Camino Feliz): la regla funciona cuando debe funcionar
    @Test
    void deberiaEncontrarTecnicoActivo() {
        Tecnico tecnicoActivo = new Tecnico(1, "Ana", "67890", "Software", true);
        when(repositorio.buscarActivo()).thenReturn(List.of(tecnicoActivo));

        List<Tecnico> activos = servicio.buscarActivo();

        assertEquals(1, activos.size());
        assertTrue(activos.get(0).isActivo());
    }

    // 2. CASO ERRÓNEO / NEGATIVO: la regla bloquea lo que tiene que bloquear
    @Test
    void noDeberiaEncontrarTecnicoInactivo() {
        when(repositorio.buscarActivo()).thenReturn(List.of());

        List<Tecnico> activos = servicio.buscarActivo();

        assertEquals(0, activos.size());
    }
}
