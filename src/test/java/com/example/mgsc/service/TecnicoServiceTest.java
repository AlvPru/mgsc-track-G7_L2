package com.example.mgsc.service;

import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.infrastucture.TecnicoRepositoryMemoria;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Clase de pruebas unitarias para TecnicoService utilizando Mockito para aislar las dependencias externas.
@ExtendWith(MockitoExtension.class)
class TecnicoServiceTest {

    // Simulación (Mock) delrepositorio para evitar el uso de datos en memoria o bases de dtos reales
    @Mock
    private TecnicoRepositoryMemoria repositorio;

    // Servicio real bajo prueba. Mockito inyecta automaticamente el mock del repositorio definido arriba
    @InjectMocks
    private TecnicoService servicio;

    // Comprueba que la acción de guardar en el servicio delega correctamente la tarea al repositorio.
    @Test
    void guardarTecnicoDebeInvocarRepositorio() {
        Tecnico tecnico = new Tecnico(1, "Juan", "Redes", true);
        
        servicio.guardar(tecnico);
        
        verify(repositorio).guardar(tecnico);
    }

    // verifica que el método de listar obtiene y retorna correctamente los datos proporcionados por el repositorio.
    @Test
    void listarDebeRetornarTodosLosTecnicos() {
        Tecnico t1 = new Tecnico(1, "Juan", "Redes", true);
        Tecnico t2 = new Tecnico(2, "Ana", "Software", false);
        when(repositorio.listar()).thenReturn(Arrays.asList(t1, t2));

        List<Tecnico> resultado = servicio.listar();

        assertEquals(2, resultado.size());
    }

    // --- REGLA DE NEGOCIO: FILTRO DE ACTIVOS ---

    // Happy Path: verifica que el filtro permite el paso a los tecnicos que estan marcados como activos.
    @Test
    void buscarActivoDebeRetornarSoloTecnicosActivos() {
        Tecnico activo = new Tecnico(1, "Ana", "Software", true);
        Tecnico inactivo = new Tecnico(2, "Luis", "Hardware", false);
        
        when(repositorio.listar()).thenReturn(Arrays.asList(activo, inactivo));

        List<Tecnico> resultado = servicio.buscarActivo();

        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).isActivo());
        assertEquals("Ana", resultado.get(0).getNombre());
    }

    // Sad Path (Filtro): Verifica que el filtro bloquea y excluye correctamente a los técnicos inactivos.
    @Test
    void buscarActivoNoDebeRetornarTecnicosInactivos() {
        Tecnico inactivo = new Tecnico(2, "Luis", "Hardware", false);
        
        when(repositorio.listar()).thenReturn(Arrays.asList(inactivo));

        List<Tecnico> resultado = servicio.buscarActivo();

        assertEquals(0, resultado.size());
    }

    // --- REGLA DE NEGOCIO: VALIDACIÓN DE GUARDADO ---

    // Sad Path (Validación): Verifica que el sistema aborta la operación si el técnico carece de nombre válido.
    @Test
    void guardarTecnicoSinNombreDebeLanzarExcepcion() {
        Tecnico tecnicoInvalido = new Tecnico(1, "", "Redes", true);

        // Se espera que la validación lance una excepción que interrumpa el flujo.
        assertThrows(IllegalArgumentException.class, () -> {
            servicio.guardar(tecnicoInvalido);
        });

        // Se garantiza la ausencia de efectos secundarios: el repositorio nunca debe ser invocado.
        verify(repositorio, never()).guardar(any());
    }
}