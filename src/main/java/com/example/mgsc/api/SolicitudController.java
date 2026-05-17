package com.example.mgsc.api;

import com.example.mgsc.api.DTOs.SolicitudRequestDTO;
import com.example.mgsc.api.DTOs.SolicitudResponseDTO;
import com.example.mgsc.dominio.Cliente;
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

    @Operation(summary = "Asignar técnico por ID", description = "Asigna un técnico activo a una solicitud usando el ID del técnico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Técnico asignado correctamente"),
        @ApiResponse(responseCode = "400", description = "El técnico está inactivo"),
        @ApiResponse(responseCode = "404", description = "Técnico o solicitud no encontrada")
    })
    @PutMapping("/{id}/tecnico/{tecnicoId}")
    public ResponseEntity<SolicitudResponseDTO> asignarTecnicoPorId(
            @PathVariable long id,
            @PathVariable long tecnicoId) {

        Tecnico tecnico = tecnicoService.buscarPorId(tecnicoId).orElse(null);
        if (tecnico == null) {
            return ResponseEntity.notFound().build();
        }
        return asignar(id, tecnico);
    }

    @Operation(summary = "Asignar técnico por DNI", description = "Asigna un técnico activo a una solicitud usando el DNI del técnico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Técnico asignado correctamente"),
        @ApiResponse(responseCode = "400", description = "El técnico está inactivo"),
        @ApiResponse(responseCode = "404", description = "Técnico o solicitud no encontrada")
    })
    @PutMapping("/{id}/tecnico/dni/{dni}")
    public ResponseEntity<SolicitudResponseDTO> asignarTecnicoPorDni(
            @PathVariable long id,
            @PathVariable String dni) {

        Tecnico tecnico = tecnicoService.buscarPorDni(dni).orElse(null);
        if (tecnico == null) {
            return ResponseEntity.notFound().build();
        }
        return asignar(id, tecnico);
    }

    @Operation(summary = "Cerrar solicitud", description = "Cierra una solicitud que está en estado EN_PROCESO")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Solicitud cerrada correctamente"),
        @ApiResponse(responseCode = "400", description = "La solicitud no está en estado EN_PROCESO"),
        @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    @PutMapping("/{id}/estado")
    public ResponseEntity<SolicitudResponseDTO> cerrar(@PathVariable long id) {
        Integer resultado = solicitudService.cerrarSolicitud(id);
        if (resultado == null || resultado < 0) {
            return ResponseEntity.badRequest().build();
        }
        return solicitudService.buscarPorId(id)
                .map(s -> ResponseEntity.ok(SolicitudMapper.toDTO(s)))
                .orElse(ResponseEntity.notFound().build());
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

    // ── helper interno ──────────────────────────────────────────────────────
    private ResponseEntity<SolicitudResponseDTO> asignar(long idSolicitud, Tecnico tecnico) {
        int resultado = solicitudService.asignarTecnico(idSolicitud, tecnico);
        if (resultado < 0) {
            return ResponseEntity.badRequest().build();
        }
        return solicitudService.buscarPorId(idSolicitud)
                .map(s -> ResponseEntity.ok(SolicitudMapper.toDTO(s)))
                .orElse(ResponseEntity.notFound().build());
    }
}
