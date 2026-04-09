package com.example.mgsc.api;

import com.example.mgsc.dominio.Tecnico;
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
class TecnicoControllerTest {

    @Mock
    private TecnicoService tecnicoService;

    @InjectMocks
    private TecnicoController controller;

    @Test
    void listarTecnicosDebeDelegarAlServicio() {
        when(tecnicoService.listar()).thenReturn(List.of());

        List<Tecnico> resultado = controller.listarTecnicos();

        assertNotNull(resultado);
        verify(tecnicoService).listar();
    }

    @Test
    void crearTecnicoDebeGuardarElTecnico() {
        Tecnico tecnico = new Tecnico(1, "Juan", "Redes", true);

        controller.crearTecnico(tecnico);

        verify(tecnicoService).guardar(tecnico);
    }
}
