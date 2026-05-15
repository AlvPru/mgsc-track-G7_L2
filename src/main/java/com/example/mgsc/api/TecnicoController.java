package com.example.mgsc.api;

import com.example.mgsc.api.dto.TecnicoRequestDTO;
import com.example.mgsc.api.dto.TecnicoResponseDTO;
import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.service.TecnicoService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tecnicos")
public class TecnicoController {

    private final TecnicoService tecnicoService;

    // Inyección de dependencias
    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    // --- ENDPOINTS (LOS CASOS DE USO) ---

    // 1. Crear un técnico (Recibe DTO, devuelve DTO)
    @PostMapping
    public ResponseEntity<TecnicoResponseDTO> crearTecnico(@Valid @RequestBody TecnicoRequestDTO requestDTO) {
        // 1. Aduana de entrada: Convertir DTO a Entidad (Dominio)
        Tecnico tecnicoNuevo = mapToEntity(requestDTO);
        
        // 2. Delegar en el servicio para que aplique reglas de negocio y guarde
        tecnicoService.guardar(tecnicoNuevo);
        
        // 3. Aduana de salida: Convertir Entidad a DTO para devolver
        TecnicoResponseDTO responseDTO = mapToResponseDTO(tecnicoNuevo);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // 2. Listar todos los técnicos (Devuelve Lista de DTOs)
    @GetMapping
    public ResponseEntity<List<TecnicoResponseDTO>> listarTecnicos() {
        List<Tecnico> tecnicos = tecnicoService.listar();
        
        List<TecnicoResponseDTO> responseDTOs = tecnicos.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
                
        return ResponseEntity.ok(responseDTOs);
    }

    // 3. Consultar solo los técnicos activos (Devuelve Lista de DTOs)
    @GetMapping("/activos")
    public ResponseEntity<List<TecnicoResponseDTO>> listarTecnicosActivos() {
        List<Tecnico> tecnicosActivos = tecnicoService.buscarActivo();
        
        List<TecnicoResponseDTO> responseDTOs = tecnicosActivos.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
                
        return ResponseEntity.ok(responseDTOs);
    }

    // --- MAPPERS (LA ADUANA) ---
    // Métodos privados manuales para traducir entre capas sin usar librerías externas.

    private Tecnico mapToEntity(TecnicoRequestDTO dto) {
        // El ID será generado por la base de datos más adelante, de momento pasamos 0 o un valor temporal
        return new Tecnico(0L, dto.getNombre(), dto.getEspecialidad(), dto.getActivo());
    }

    private TecnicoResponseDTO mapToResponseDTO(Tecnico entidad) {
        return new TecnicoResponseDTO(
                entidad.getId(),
                entidad.getNombre(),
                entidad.getEspecialidad(),
                entidad.isActivo()
        );
    }
}