package com.example.mgsc.api;

import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.service.TecnicoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tecnicos")
public class TecnicoController {

    private final TecnicoService tecnicoService;

    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    // Cumple el requisito: "Listar técnicos disponibles"
    @GetMapping
    public List<Tecnico> listarTecnicos() {
        return tecnicoService.listar();
    }

    // Cumple el requisito: "Crear técnico"
    @PostMapping
    public void crearTecnico(@RequestBody Tecnico tecnico) {
        tecnicoService.guardar(tecnico);
    }
}