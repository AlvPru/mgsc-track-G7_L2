package com.example.mgsc.service;

import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.infrastucture.TecnicoRepositoryMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TecnicoServiceTest {

    private TecnicoService tecnicoService;
    private TecnicoRepositoryMemoria repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new TecnicoRepositoryMemoria();
        tecnicoService = new TecnicoService(repositorio);
    }

    @Test
    void deberiaGuardarUnTecnico() {
        Tecnico tecnico = new Tecnico(1, "Juan", "Redes", true);
        tecnicoService.guardar(tecnico);
        
        List<Tecnico> lista = tecnicoService.listar();
        assertEquals(1, lista.size());
    }

    // --- SEPARACIÓN DE LA REGLA DE NEGOCIO ---

    // 1. CASO ACERTADO (Camino Feliz): Comprueba que la regla funciona cuando debe funcionar
    @Test
    void deberiaEncontrarTecnicoActivo() {
        Tecnico tecnicoActivo = new Tecnico(1, "Ana", "Software", true);
        tecnicoService.guardar(tecnicoActivo);
        
        List<Tecnico> activos = tecnicoService.buscarActivo();
        
        // Esperamos encontrar 1 técnico y que su estado sea "true"
        assertEquals(1, activos.size());
        assertTrue(activos.get(0).isActivo());
    }

    // 2. CASO ERRÓNEO / NEGATIVO: Comprueba que la regla bloquea lo que tiene que bloquear
    @Test
    void noDeberiaEncontrarTecnicoInactivo() {
        Tecnico tecnicoInactivo = new Tecnico(2, "Luis", "Hardware", false);
        tecnicoService.guardar(tecnicoInactivo);
        
        List<Tecnico> activos = tecnicoService.buscarActivo();
        
        // Esperamos encontrar 0 técnicos en la lista de activos, porque el sistema lo ha filtrado
        assertEquals(0, activos.size());
    }
}