package com.example.mgsc.unitarios.infrastucture;

import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.dominio.entidades.TecnicoEntity;
import com.example.mgsc.infrastucture.TecnicoRepositoryAdapter;
import com.example.mgsc.infrastucture.TecnicoRepositoryJPA;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TecnicoRepositoryAdapterTest {

    @Mock
    private TecnicoRepositoryJPA jpaRepo;

    @InjectMocks
    private TecnicoRepositoryAdapter adapter;

    private Tecnico tecnicoBase() {
        return new Tecnico(1L, "Ana", "67890", "Software", true);
    }

    @Test
    void guardarDebeGuardarEntityYActualizarId() {
        Tecnico tecnico = new Tecnico("Ana", "67890", "Software", true);
        // id = -1 (nuevo)

        TecnicoEntity savedEntity = TecnicoEntity.fromDomain(tecnicoBase()); // id = 1
        when(jpaRepo.save(any())).thenReturn(savedEntity);

        Tecnico resultado = adapter.guardar(tecnico);

        verify(jpaRepo).save(any());
        assertEquals(1L, resultado.getId());
    }

    @Test
    void buscarPorIdDebeRetornarTecnicoExistente() {
        TecnicoEntity entity = TecnicoEntity.fromDomain(tecnicoBase());
        when(jpaRepo.findById(1L)).thenReturn(Optional.of(entity));

        Optional<Tecnico> resultado = adapter.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Ana", resultado.get().getNombre());
    }

    @Test
    void buscarPorIdDebeRetornarVacioSiNoExiste() {
        when(jpaRepo.findById(99L)).thenReturn(Optional.empty());

        Optional<Tecnico> resultado = adapter.buscarPorId(99L);

        assertFalse(resultado.isPresent());
    }

    @Test
    void buscarActivoDebeRetornarSoloActivos() {
        TecnicoEntity entity = TecnicoEntity.fromDomain(tecnicoBase());
        when(jpaRepo.findByActivoTrue()).thenReturn(List.of(entity));

        List<Tecnico> resultado = adapter.buscarActivo();

        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).isActivo());
    }

    @Test
    void listarDebeRetornarTodosLosTecnicos() {
        TecnicoEntity entity = TecnicoEntity.fromDomain(tecnicoBase());
        when(jpaRepo.findAll()).thenReturn(List.of(entity));

        List<Tecnico> resultado = adapter.listar();

        assertEquals(1, resultado.size());
        assertEquals("Ana", resultado.get(0).getNombre());
    }

    @Test
    void buscarPorDniDebeRetornarTecnicoExistente() {
        TecnicoEntity entity = TecnicoEntity.fromDomain(tecnicoBase());
        when(jpaRepo.findByDni("67890")).thenReturn(Optional.of(entity));

        Optional<Tecnico> resultado = adapter.buscarPorDni("67890");

        assertTrue(resultado.isPresent());
        assertEquals("Ana", resultado.get().getNombre());
    }

    @Test
    void buscarPorDniDebeRetornarVacioSiNoExiste() {
        when(jpaRepo.findByDni("99999")).thenReturn(Optional.empty());

        Optional<Tecnico> resultado = adapter.buscarPorDni("99999");

        assertFalse(resultado.isPresent());
    }
}
