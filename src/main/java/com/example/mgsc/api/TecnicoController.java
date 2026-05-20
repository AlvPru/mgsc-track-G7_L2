package com.example.mgsc.api;

import com.example.mgsc.api.DTOs.TecnicoRequestDTO;
import com.example.mgsc.api.DTOs.TecnicoResponseDTO;
import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.service.TecnicoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tecnicos")
public class TecnicoController {

    private final TecnicoService tecnicoService;

    // Inyección de dependencias
    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    @PostMapping
    public TecnicoResponseDTO crearTecnico(@RequestBody TecnicoRequestDTO request) {
        try {
            Tecnico tecnico = tecnicoService.crearTecnico(
                    request.getNombre(),
                    request.getDni(),
                    request.getEspecialidad(),
                    request.getEstado());
            return toResponseDTO(tecnico);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @GetMapping
    public List<TecnicoResponseDTO> listarTecnicos() {
        return tecnicoService.listar().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/activos")
    public List<TecnicoResponseDTO> listarTecnicosActivos() {
        return tecnicoService.buscarActivo().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TecnicoResponseDTO obtenerTecnico(@PathVariable long id) {
        return toResponseDTO(tecnicoService.buscarPorIdOrThrow(id));
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<TecnicoResponseDTO> buscarPorDni(@PathVariable String dni) {
        return tecnicoService.buscarPorDni(dni)
                .map(t -> ResponseEntity.ok(toResponseDTO(t)))
                .orElse(ResponseEntity.notFound().build());
    }

    private TecnicoResponseDTO toResponseDTO(Tecnico entidad) {
        return new TecnicoResponseDTO(
                entidad.getId(),
                entidad.getNombre(),
                entidad.getDni(),
                entidad.getEspecialidad(),
                entidad.isActivo());
    }
}