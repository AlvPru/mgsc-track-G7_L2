package com.example.mgsc.service;

import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.infrastucture.interfaces.TecnicoRepositoryPort;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {
    private final TecnicoRepositoryPort tecnicoRepository;

    public TecnicoService(TecnicoRepositoryPort tecnicoRepository) {
        this.tecnicoRepository = tecnicoRepository;
    }

    public Tecnico guardar(Tecnico tecnico) {
        return tecnicoRepository.guardar(tecnico);
    }

    public List<Tecnico> buscarActivo() {
        return tecnicoRepository.buscarActivo();
    }

    public List<Tecnico> listar() {
        return tecnicoRepository.listar();
    }

    public Optional<Tecnico> buscarPorDni(String dni) {
        return tecnicoRepository.buscarPorDni(dni);
    }

    public Tecnico crearTecnico(String nombre, String dni, String especialidad, boolean activo) {
        if (tecnicoRepository.buscarPorDni(dni).isPresent()) {
            throw new IllegalArgumentException("Ya existe un tecnico con DNI: " + dni);
        }
        Tecnico tecnico = new Tecnico(nombre, dni, especialidad, activo);
        return tecnicoRepository.guardar(tecnico);
    }

    public Optional<Tecnico> buscarPorId(long id) {
        return tecnicoRepository.buscarPorId(id);
    }

    public Tecnico buscarPorIdOrThrow(long id) {
        return tecnicoRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Tecnico no encontrado: " + id));
    }
}