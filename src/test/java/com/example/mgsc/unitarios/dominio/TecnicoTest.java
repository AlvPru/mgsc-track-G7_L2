package com.example.mgsc.unitarios.dominio;

import org.junit.jupiter.api.Test;

import com.example.mgsc.dominio.Tecnico;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Tag;
@Tag("unitario")
class TecnicoTest {

    @Test
    void debeCrearTecnicoConDatosValidos() {
        Tecnico tecnico = new Tecnico(1, "Ana", "Electricidad", true);
        assertEquals(1, tecnico.getId());
        assertEquals("Ana", tecnico.getNombre());
        assertEquals("Electricidad", tecnico.getEspecialidad());
        assertTrue(tecnico.isActivo());
    }

    @Test
    void setEspecialidadDebeActualizar() {
        Tecnico tecnico = new Tecnico(1, "Ana", "Electricidad", true);
        tecnico.setEspecialidad("Redes");
        assertEquals("Redes", tecnico.getEspecialidad());
    }

    @Test
    void setActivoDebeActualizar() {
        Tecnico tecnico = new Tecnico(1, "Ana", "Electricidad", true);
        tecnico.setActivo(false);
        assertFalse(tecnico.isActivo());
    }

    @Test
    void setIdDebeActualizar() {
        Tecnico tecnico = new Tecnico(1, "Ana", "Electricidad", true);
        tecnico.setId(99);
        assertEquals(99, tecnico.getId());
    }

    @Test
    void setNombreDebeActualizar() {
        Tecnico tecnico = new Tecnico(1, "Ana", "Electricidad", true);
        tecnico.setNombre("Luis");
        assertEquals("Luis", tecnico.getNombre());
    }
}
