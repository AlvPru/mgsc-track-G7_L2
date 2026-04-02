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
        // Le pasamos: ID (1), Nombre ("Juan"), Especialidad ("Redes"), y Activo (true)
        Tecnico tecnico = new Tecnico(1, "Juan", "Redes", true);
        
        tecnicoService.guardar(tecnico);
        
        List<Tecnico> lista = tecnicoService.listar();
        assertEquals(1, lista.size());
    }

    @Test
    void deberiaFiltrarTecnicosActivos() {
        // Técnico 1: Activo (true)
        Tecnico tecnicoActivo = new Tecnico(1, "Ana", "Software", true);
        
        // Técnico 2: Inactivo (false)
        Tecnico tecnicoInactivo = new Tecnico(2, "Luis", "Hardware", false);
        
        tecnicoService.guardar(tecnicoActivo);
        tecnicoService.guardar(tecnicoInactivo);
        
        List<Tecnico> activos = tecnicoService.buscarActivo();
        
        assertEquals(1, activos.size());
        assertTrue(activos.get(0).isActivo());
    }
}