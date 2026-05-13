package com.example.mgsc.api;

import com.example.mgsc.api.DTOs.AsignarTecnicoRequestDTO;
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

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/solicitudes")
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

    // POST /api/solicitudes — crear solicitud
    @PostMapping
    public ResponseEntity<SolicitudResponseDTO> crear(@RequestBody SolicitudRequestDTO request) {
        Cliente cliente = clienteService.buscarPorIdOrThrow((int) request.getClienteId());
        Solicitud solicitud = new Solicitud(request.getDescripcion(), cliente);
        solicitudService.guardar(solicitud);
        return ResponseEntity.ok(SolicitudMapper.toDTO(solicitud));
    }

    // GET /api/solicitudes — listar todas
    @GetMapping
    public ResponseEntity<List<SolicitudResponseDTO>> listar() {
        List<SolicitudResponseDTO> lista = solicitudService.listar().stream()
                .map(SolicitudMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    // GET /api/solicitudes/{id} — consultar por id
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudResponseDTO> consultar(@PathVariable long id) {
        return solicitudService.buscarPorId(id)
                .map(s -> ResponseEntity.ok(SolicitudMapper.toDTO(s)))
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/solicitudes/{id}/tecnico — asignar técnico
    @PutMapping("/{id}/tecnico")
    public ResponseEntity<SolicitudResponseDTO> asignarTecnico(
            @PathVariable long id,
            @RequestBody AsignarTecnicoRequestDTO request) {

        Tecnico tecnico = tecnicoService.buscarPorId(request.getTecnicoId()).orElse(null);
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

    // PUT /api/solicitudes/{id}/estado — cambiar estado
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

    // PATCH /api/solicitudes/{id}/reabrir — reabrir solicitud cerrada
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
