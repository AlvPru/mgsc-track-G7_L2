package com.example.mgsc.api;

import com.example.mgsc.api.DTOs.TecnicoRequestDTO;
import com.example.mgsc.api.DTOs.CambiarEstadoRequestDTO;
import com.example.mgsc.api.DTOs.SolicitudRequestDTO;
import com.example.mgsc.api.DTOs.SolicitudResponseDTO;
import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.EstadoSolicitud;
import com.example.mgsc.dominio.Solicitud;
import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.service.ClienteService;
import com.example.mgsc.service.SolicitudService;
import com.example.mgsc.service.TecnicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/solicitudes")
@Tag(name = "Solicitudes", description = "Gestión de solicitudes de servicio técnico")
public class SolicitudController {

    private final SolicitudService solicitudService;
    private final ClienteService clienteService;
    private final TecnicoService tecnicoService;

    public SolicitudController(SolicitudService solicitudService,
                               ClienteService clienteService,
                               TecnicoService tecnicoService) {
        this.solicitudService = solicitudService;
        this.clienteService = clienteService;
        this.tecnicoService = tecnicoService;
    }

    @Operation(summary = "Crear solicitud", description = "Crea una nueva solicitud de servicio para un cliente existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Solicitud creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PostMapping
    public ResponseEntity<SolicitudResponseDTO> crear(@RequestBody SolicitudRequestDTO request) {
        Cliente cliente = clienteService.buscarPorIdOrThrow(request.getClienteId());
        Solicitud solicitud = new Solicitud(request.getDescripcion(), cliente);
        solicitudService.guardar(solicitud);
        return ResponseEntity.ok(SolicitudMapper.toDTO(solicitud));
    }

    @Operation(summary = "Listar solicitudes", description = "Devuelve todas las solicitudes registradas. Las de clientes premium aparecen primero")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<SolicitudResponseDTO>> listar() {
        List<SolicitudResponseDTO> lista = solicitudService.listar().stream()
                .map(SolicitudMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Consultar solicitud", description = "Obtiene el detalle de una solicitud por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Solicitud encontrada"),
        @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudResponseDTO> consultar(@PathVariable long id) {
        return solicitudService.buscarPorId(id)
                .map(s -> ResponseEntity.ok(SolicitudMapper.toDTO(s)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Asignar técnico", description = "Asigna un técnico activo a una solicitud. El técnico debe estar activo y la solicitud debe existir")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Técnico asignado correctamente"),
        @ApiResponse(responseCode = "400", description = "El técnico está inactivo o la solicitud no existe"),
        @ApiResponse(responseCode = "404", description = "Técnico no encontrado")
    })
    @PutMapping("/{id}/tecnico")
    public ResponseEntity<SolicitudResponseDTO> asignarTecnico(
            @PathVariable long id,
            @RequestBody TecnicoRequestDTO request) {

        Tecnico tecnico = tecnicoService.buscarPorDni(request.getDni()).orElse(null);
        if (tecnico == null) {
            return ResponseEntity.notFound().build();
        }
        int resultado = solicitudService.asignarTecnico(id, tecnico);
        if (resultado < 0) {
            return ResponseEntity.badRequest().build();
        }
        return solicitudService.buscarPorId(id)
                .map(s -> ResponseEntity.ok(SolicitudMapper.toDTO(s)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cambiar estado", description = "Actualiza el estado de una solicitud. Valores válidos: PENDIENTE, EN_PROCESO, CERRADA")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Estado inválido"),
        @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    @PutMapping("/{id}/estado")
    public ResponseEntity<SolicitudResponseDTO> cambiarEstado(
            @PathVariable long id,
            @RequestBody CambiarEstadoRequestDTO request) {
        try {
            EstadoSolicitud nuevoEstado = EstadoSolicitud.valueOf(request.getEstado());
            Solicitud solicitud = solicitudService.cambiarEstado(id, nuevoEstado);
            if (solicitud == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(SolicitudMapper.toDTO(solicitud));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Reabrir solicitud", description = "Reabre una solicitud que estaba en estado CERRADA. Vuelve al estado EN_PROCESO")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Solicitud reabierta correctamente"),
        @ApiResponse(responseCode = "400", description = "La solicitud no está en estado CERRADA"),
        @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    @PatchMapping("/{id}/reabrir")
    public ResponseEntity<SolicitudResponseDTO> reabrir(@PathVariable long id) {
        try {
            Solicitud solicitud = solicitudService.reabrirSolicitud(id);
            if (solicitud == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(SolicitudMapper.toDTO(solicitud));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
