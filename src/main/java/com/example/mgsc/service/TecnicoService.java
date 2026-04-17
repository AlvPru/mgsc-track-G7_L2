package com.example.mgsc.service;

import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.infrastucture.TecnicoRepositoryMemoria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// Servicio encargado de orquestar la lógica de negocio y las reglas de validacion de los Técnicos.
@Service
public class TecnicoService {

    private final TecnicoRepositoryMemoria repositorio;

    // Inyección de dependencias a través del constructor.
    public TecnicoService(TecnicoRepositoryMemoria repositorio) {
        this.repositorio = repositorio;
    }

    // Registra un nuevo técnico. Incluye una validación de seguridad (Sad Path) 
    // que impde guardar técnicos sin nombre.
    public void guardar(Tecnico tecnico) {
        if (tecnico.getNombre() == null || tecnico.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del técnico no puede estar vacío");
        }
        repositorio.guardar(tecnico);
    }

    // Devuelve el listado completo sin aplicar filtros de negocio.
    public List<Tecnico> listar() {
        return repositorio.listar();
    }

    // Aplica la regla de negocio para recuperar exclusivamente a los técnicos operativos
    public List<Tecnico> buscarActivo() {
        return repositorio.listar().stream()
                .filter(Tecnico::isActivo)
                .collect(Collectors.toList());
    }
}