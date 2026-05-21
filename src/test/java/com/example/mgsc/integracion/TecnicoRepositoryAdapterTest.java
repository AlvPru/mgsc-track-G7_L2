package com.example.mgsc.integracion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.infrastucture.TecnicoRepositoryAdapter;
import com.example.mgsc.infrastucture.TecnicoRepositoryJPA;

@DataJpaTest
@ActiveProfiles({"test", "jpa"})
@Import(TecnicoRepositoryAdapter.class)
class TecnicoRepositoryAdapterTest {

    @Autowired
    private TecnicoRepositoryAdapter adapter;

    @Autowired
    private TecnicoRepositoryJPA jpaRepository;

    @Test
    void guardarYRecuperarTecnico() {
        Tecnico tecnico = new Tecnico("Fernando", "11223344X", "Software", true);
        
        Tecnico guardado = adapter.guardar(tecnico);
        
        assertNotNull(guardado);
        Optional<Tecnico> recuperado = adapter.buscarPorId(guardado.getId());
        assertTrue(recuperado.isPresent());
        assertEquals("Fernando", recuperado.get().getNombre());
        assertEquals("Software", recuperado.get().getEspecialidad());
        assertTrue(recuperado.get().isActivo());
    }

    @Test
    void buscarTecnicosActivos() {
        Tecnico t1 = new Tecnico("Ana", "55667788Y", "Electricidad", true);
        Tecnico t2 = new Tecnico("Luis", "99001122Z", "Redes", false);
        
        adapter.guardar(t1);
        adapter.guardar(t2);
        
        List<Tecnico> activos = adapter.buscarActivo();
        
        assertEquals(1, activos.size());
        assertEquals("Ana", activos.get(0).getNombre());
    }
}