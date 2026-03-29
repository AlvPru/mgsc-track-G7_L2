package com.example.mgsc.service;

import com.example.mgsc.dominio.Tecnico;
import java.util.List;

public class TecnicoService {
    private final TecnicoRepositoryPort tecnicoRepository;

    public TecnicoService(TecnicoRepositoryPort tecnicoRepository) {
        this.tecnicoRepository = tecnicoRepository;
    }

    public void guardar(Tecnico tecnico) {
        tecnicoRepository.guardar(tecnico);
    }

    public List<Tecnico> buscarActivo() {
        return tecnicoRepository.buscarActivo();
    }

    public List<Tecnico> listar() {
        return tecnicoRepository.listar();
    }
}