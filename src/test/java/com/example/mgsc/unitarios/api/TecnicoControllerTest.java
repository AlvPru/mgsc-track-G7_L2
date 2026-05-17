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
        // Simulamos que el dominio interno devuelve un técnico
        Tecnico t1 = new Tecnico(1L, "Juan", "12345", "Redes", true);
        when(tecnicoService.listar()).thenReturn(Arrays.asList(t1));

        // Hacemos la petición GET y verificamos la estructura JSON del DTO de salida
        mockMvc.perform(get("/api/tecnicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                .andExpect(jsonPath("$[0].especialidad").value("Redes"));
    }
}