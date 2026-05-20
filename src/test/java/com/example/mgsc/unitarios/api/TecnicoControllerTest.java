package com.example.mgsc.unitarios.api;

import com.example.mgsc.api.TecnicoController;
import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.service.TecnicoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Fase 3: Usamos WebMvcTest para probar exclusivamente la capa web sin levantar toda la BD.
@WebMvcTest(TecnicoController.class)
public class TecnicoControllerTest {

    @Autowired
    private MockMvc mockMvc; // Herramienta para simular peticiones HTTP 

    @MockBean
    private TecnicoService tecnicoService; // Mockeamos el servicio 

    @Test
    void crearTecnicoDebeRetornarCreated() throws Exception {
        Tecnico tecnico = new Tecnico(1L, "Juan", "12345", "Redes", true);
        when(tecnicoService.crearTecnico(any(), any(), any(), anyBoolean())).thenReturn(tecnico);

        String requestJson = """
            {
                "nombre": "Juan",
                "dni": "12345",
                "especialidad": "Redes",
                "activo": true
            }
            """;

        mockMvc.perform(post("/api/tecnicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    void listarTecnicosDebeRetornarOkYEstructuraJson() throws Exception {
        Tecnico t1 = new Tecnico(1L, "Juan", "12345", "Redes", true);
        when(tecnicoService.listar()).thenReturn(Arrays.asList(t1));

        mockMvc.perform(get("/api/tecnicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                .andExpect(jsonPath("$[0].especialidad").value("Redes"));
    }

    @Test
    void listarTecnicosActivosDebeRetornarOk() throws Exception {
        Tecnico t1 = new Tecnico(1L, "Ana", "67890", "Software", true);
        when(tecnicoService.buscarActivo()).thenReturn(Arrays.asList(t1));

        mockMvc.perform(get("/api/tecnicos/activos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Ana"))
                .andExpect(jsonPath("$[0].especialidad").value("Software"));
    }

    @Test
    void obtenerTecnicoPorIdDebeRetornarDTO() throws Exception {
        Tecnico t1 = new Tecnico(1L, "Juan", "12345", "Redes", true);
        when(tecnicoService.buscarPorIdOrThrow(1L)).thenReturn(t1);

        mockMvc.perform(get("/api/tecnicos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.especialidad").value("Redes"));
    }

    @Test
    void buscarTecnicoPorDniDebeRetornarDTO() throws Exception {
        Tecnico t1 = new Tecnico(1L, "Juan", "12345", "Redes", true);
        when(tecnicoService.buscarPorDni("12345")).thenReturn(Optional.of(t1));

        mockMvc.perform(get("/api/tecnicos/dni/12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.especialidad").value("Redes"));
    }
}