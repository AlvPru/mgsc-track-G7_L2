package com.example.mgsc.infraestructura;

import com.example.mgsc.domain.Tecnico;
import com.example.mgsc.puertos.RepositorioTecnico;
import java.util.List;
import java.util.UUID;

public class RepositorioTecnicoJPA implements RepositorioTecnico {
    private final JpaTecnicoRepository springDataRepo;

    public RepositorioTecnicoJPA(JpaTecnicoRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public void guardar(Tecnico tecnico) {
    }

    @Override
    public Tecnico obtenerPorId(UUID id) {
        return null;
    }

    @Override
    public List<Tecnico> obtenerTodosActivos() {
        return null;
    }

    @Override
    public List<Tecnico> obtenerTodos() {
        return null;
    }
}
