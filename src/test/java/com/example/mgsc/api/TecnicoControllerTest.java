package com.example.mgsc.api;

import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.service.TecnicoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TecnicoControllerTest {

    @Mock
    private TecnicoService tecnicoService;

    @InjectMocks
    private TecnicoController controlador;

    @Test
    void listarTecnicosDebeRetornarLista() {
        List<Tecnico> tecnicos = Arrays.asList(
            new Tecnico(1, "Ana", "Electricidad", true),
            new Tecnico(2, "Luis", "Redes", false)
        );
        when(tecnicoService.listar()).thenReturn(tecnicos);

        List<Tecnico> resultado = controlador.listarTecnicos();

        assertEquals(2, resultado.size());
    }

    @Test
    void crearTecnicoDebeInvocarServicio() {
        Tecnico tecnico = new Tecnico(1, "Ana", "Electricidad", true);

        controlador.crearTecnico(tecnico);

        verify(tecnicoService).guardar(tecnico);
    }
}
