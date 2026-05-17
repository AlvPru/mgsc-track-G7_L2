package com.example.mgsc.unitarios.api;

import com.example.mgsc.api.DTOs.CambiarEstadoRequestDTO;
import com.example.mgsc.api.DTOs.TecnicoRequestDTO;
import com.example.mgsc.api.DTOs.SolicitudRequestDTO;
import com.example.mgsc.api.SolicitudController;
import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.EstadoSolicitud;
import com.example.mgsc.dominio.Solicitud;
import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.dominio.TipoCliente;
import com.example.mgsc.service.ClienteService;
import com.example.mgsc.service.SolicitudService;
import com.example.mgsc.service.TecnicoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SolicitudController.class)
@Tag("pruebaWebMvc")
class SolicitudControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SolicitudService solicitudService;

    @MockBean
    private ClienteService clienteService;

    @MockBean
    private TecnicoService tecnicoService;

    private Cliente cliente;
    private Solicitud solicitud;
    private Tecnico tecnicoActivo;
    private Tecnico tecnicoInactivo;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("Carlos", "12345", "carlos@email.com", TipoCliente.STANDARD);
        cliente.setId(1);
        solicitud = new Solicitud("Avería en instalación", cliente);
        tecnicoActivo = new Tecnico(1, "Juan", "12345", "Electricidad", true);
        tecnicoInactivo = new Tecnico(2, "Maria", "67890", "Plomeria", false);
    }

    // --- GET /api/solicitudes ---

    @Test
    void listarSolicitudesDevuelveListaDTO() throws Exception {
        when(solicitudService.listar()).thenReturn(List.of(solicitud));

        mockMvc.perform(get("/api/solicitudes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].descripcion").value("Avería en instalación"))
                .andExpect(jsonPath("$[0].estado").value("PENDIENTE"))
                .andExpect(jsonPath("$[0].clienteNombre").value("Carlos"));
    }

    // --- GET /api/solicitudes/{id} ---

    @Test
    void consultarSolicitudExistenteDevuelveDTO() throws Exception {
        when(solicitudService.buscarPorId(solicitud.getId())).thenReturn(Optional.of(solicitud));

        mockMvc.perform(get("/api/solicitudes/" + solicitud.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Avería en instalación"))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"))
                .andExpect(jsonPath("$.clienteNombre").value("Carlos"));
    }

    @Test
    void consultarSolicitudInexistenteDevuelveNotFound() throws Exception {
        when(solicitudService.buscarPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/solicitudes/99"))
                .andExpect(status().isNotFound());
    }

    // --- POST /api/solicitudes ---

    @Test
    void crearSolicitudDevuelveDTO() throws Exception {
        when(clienteService.buscarPorIdOrThrow(anyLong())).thenReturn(cliente);

        SolicitudRequestDTO request = new SolicitudRequestDTO("Avería en instalación", 1L);

        mockMvc.perform(post("/api/solicitudes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Avería en instalación"))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));
    }

    // --- PUT /api/solicitudes/{id}/tecnico ---

    @Test
    void asignarTecnicoActivoDevuelveOk() throws Exception {
        solicitud.setEstado(EstadoSolicitud.EN_PROCESO);
        solicitud.setTecnico(tecnicoActivo);

        when(tecnicoService.buscarPorDni("12345")).thenReturn(Optional.of(tecnicoActivo));
        when(solicitudService.asignarTecnico(anyLong(), eq(tecnicoActivo))).thenReturn(0);
        when(solicitudService.buscarPorId(anyLong())).thenReturn(Optional.of(solicitud));

        TecnicoRequestDTO dto = new TecnicoRequestDTO();
        dto.setDni("12345");

        mockMvc.perform(put("/api/solicitudes/" + solicitud.getId() + "/tecnico")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("EN_PROCESO"));
    }

    @Test
    void asignarTecnicoInactivoDevuelveBadRequest() throws Exception {
        when(tecnicoService.buscarPorDni("67890")).thenReturn(Optional.of(tecnicoInactivo));
        when(solicitudService.asignarTecnico(anyLong(), eq(tecnicoInactivo))).thenReturn(-1);

        TecnicoRequestDTO dto = new TecnicoRequestDTO();
        dto.setDni("67890");

        mockMvc.perform(put("/api/solicitudes/" + solicitud.getId() + "/tecnico")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    // --- PUT /api/solicitudes/{id}/estado ---

    @Test
    void cambiarEstadoValidoDevuelveOk() throws Exception {
        solicitud.setEstado(EstadoSolicitud.EN_PROCESO);
        when(solicitudService.cambiarEstado(anyLong(), eq(EstadoSolicitud.EN_PROCESO)))
                .thenReturn(solicitud);

        CambiarEstadoRequestDTO dto = new CambiarEstadoRequestDTO();
        dto.setEstado("EN_PROCESO");

        mockMvc.perform(put("/api/solicitudes/" + solicitud.getId() + "/estado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("EN_PROCESO"));
    }

    @Test
    void cambiarEstadoInvalidoDevuelveBadRequest() throws Exception {
        CambiarEstadoRequestDTO dto = new CambiarEstadoRequestDTO();
        dto.setEstado("ESTADO_INEXISTENTE");

        mockMvc.perform(put("/api/solicitudes/" + solicitud.getId() + "/estado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    // --- PATCH /api/solicitudes/{id}/reabrir ---

    @Test
    void reabrirSolicitudDevuelveOk() throws Exception {
        solicitud.setEstado(EstadoSolicitud.EN_PROCESO);
        when(solicitudService.reabrirSolicitud(solicitud.getId())).thenReturn(solicitud);

        mockMvc.perform(patch("/api/solicitudes/" + solicitud.getId() + "/reabrir"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("EN_PROCESO"));
    }

    @Test
    void reabrirSolicitudInexistenteDevuelveNotFound() throws Exception {
        when(solicitudService.reabrirSolicitud(99L)).thenReturn(null);

        mockMvc.perform(patch("/api/solicitudes/99/reabrir"))
                .andExpect(status().isNotFound());
    }
}
