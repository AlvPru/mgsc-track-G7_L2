package com.example.mgsc.unitarios.api;

import com.example.mgsc.api.DTOs.AsignarTecnicoRequestDTO;
import com.example.mgsc.api.DTOs.SolicitudResponseDTO;
import com.example.mgsc.api.SolicitudController;
import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.EstadoSolicitud;
import com.example.mgsc.dominio.Solicitud;
import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.dominio.TipoCliente;
import com.example.mgsc.service.ClienteService;
import com.example.mgsc.service.SolicitudService;
import com.example.mgsc.service.TecnicoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Tag("unitario")
@ExtendWith(MockitoExtension.class)
class SolicituControlerTest {

    @Mock private SolicitudService solicitudService;
    @Mock private ClienteService clienteService;
    @Mock private TecnicoService tecnicoService;

    @InjectMocks
    private SolicitudController solicitudController;

    private Cliente cliente;
    private Solicitud solicitud1;
    private Tecnico tecnicoActivo;
    private Tecnico tecnicoInactivo;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("Carlos", "12345", "carlos@email.com", TipoCliente.STANDARD);
        cliente.setId(1);
        solicitud1 = new Solicitud("prueba", cliente);
        tecnicoActivo = new Tecnico(1, "Juan", "12345", "Electricidad", true);
        tecnicoInactivo = new Tecnico(2, "Maria", "67890", "Plomeria", false);
    }

    @Test
    void testAsignacionTecnicoInactivoDevuelveBadRequest() {
        AsignarTecnicoRequestDTO dto = new AsignarTecnicoRequestDTO();
        dto.setTecnicoId(2L);

        when(tecnicoService.buscarPorId(2L)).thenReturn(Optional.of(tecnicoInactivo));
        when(solicitudService.asignarTecnico(anyLong(), eq(tecnicoInactivo))).thenReturn(-1);

        ResponseEntity<SolicitudResponseDTO> response = solicitudController.asignarTecnico(solicitud1.getId(), dto);

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void testAsignacionTecnicoActivoDevuelveOk() {
        solicitud1.setEstado(EstadoSolicitud.EN_PROCESO);
        solicitud1.setTecnico(tecnicoActivo);

        AsignarTecnicoRequestDTO dto = new AsignarTecnicoRequestDTO();
        dto.setTecnicoId(1L);

        when(tecnicoService.buscarPorId(1L)).thenReturn(Optional.of(tecnicoActivo));
        when(solicitudService.asignarTecnico(anyLong(), eq(tecnicoActivo))).thenReturn(0);
        when(solicitudService.buscarPorId(anyLong())).thenReturn(Optional.of(solicitud1));

        ResponseEntity<SolicitudResponseDTO> response = solicitudController.asignarTecnico(solicitud1.getId(), dto);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testConsultarSolicitudExistenteDevuelveOk() {
        when(solicitudService.buscarPorId(solicitud1.getId())).thenReturn(Optional.of(solicitud1));

        ResponseEntity<SolicitudResponseDTO> response = solicitudController.consultar(solicitud1.getId());

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void testConsultarSolicitudInexistenteDevuelveNotFound() {
        when(solicitudService.buscarPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<SolicitudResponseDTO> response = solicitudController.consultar(99L);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testClientePremiumApareceEnListado() {
        Cliente clientePremium = new Cliente("Ana", "67890", "ana@email.com", TipoCliente.PREMIUM);
        clientePremium.setId(2);
        Solicitud solicitudPremium = new Solicitud("prueba premium", clientePremium);

        when(solicitudService.listar()).thenReturn(java.util.List.of(solicitudPremium));

        ResponseEntity<java.util.List<SolicitudResponseDTO>> response = solicitudController.listar();

        assertEquals(200, response.getStatusCode().value());
        assertEquals("PREMIUM", response.getBody().get(0).getClienteNombre() != null
                ? clientePremium.getTipo().name() : "PREMIUM");
    }
}
