package com.example.mgsc.unitarios.infrastucture;

import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.infrastucture.TecnicoRepositoryMemoria;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TecnicoRepositoryMemoriaTest {

    private TecnicoRepositoryMemoria repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new TecnicoRepositoryMemoria();
    }

    @Test
    void guardarTecnicoNuevoAsignaIdAutomatico() {
        Tecnico tecnico = new Tecnico("Juan", "12345", "Redes", true);
        // id = -1 por el constructor sin id (ver Persona)

        repositorio.guardar(tecnico);

        assertTrue(tecnico.getId() > 0, "Debe asignarse un id positivo");
    }

    @Test
    void guardarTecnicoConIdExistenteNoModificaId() {
        Tecnico tecnico = new Tecnico(42, "Juan", "12345", "Redes", true);

        repositorio.guardar(tecnico);

        assertEquals(42, tecnico.getId());
    }

    @Test
    void listarRetornaTodosLosTecnicos() {
        repositorio.guardar(new Tecnico("Ana", "11111", "Software", true));
        repositorio.guardar(new Tecnico("Pedro", "22222", "Plomería", false));

        List<Tecnico> todos = repositorio.listar();

        assertEquals(2, todos.size());
    }

    @Test
    void buscarActivoRetornaSoloTecnicosActivos() {
        repositorio.guardar(new Tecnico("Activo", "11111", "Redes", true));
        repositorio.guardar(new Tecnico("Inactivo", "22222", "Plomería", false));

        List<Tecnico> activos = repositorio.buscarActivo();

        assertEquals(1, activos.size());
        assertTrue(activos.get(0).isActivo());
    }

    @Test
    void buscarPorDniRetornaTecnicoExistente() {
        repositorio.guardar(new Tecnico("Juan", "12345", "Redes", true));

        Optional<Tecnico> resultado = repositorio.buscarPorDni("12345");

        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
    }

    @Test
    void buscarPorDniRetornaVacioSiNoExiste() {
        Optional<Tecnico> resultado = repositorio.buscarPorDni("99999");

        assertFalse(resultado.isPresent());
    }

    @Test
    void buscarPorIdRetornaTecnicoExistente() {
        Tecnico tecnico = new Tecnico("Juan", "12345", "Redes", true);
        repositorio.guardar(tecnico);

        Optional<Tecnico> resultado = repositorio.buscarPorId(tecnico.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
    }

    @Test
    void buscarPorIdRetornaVacioSiNoExiste() {
        Optional<Tecnico> resultado = repositorio.buscarPorId(999L);

        assertFalse(resultado.isPresent());
    }
}
