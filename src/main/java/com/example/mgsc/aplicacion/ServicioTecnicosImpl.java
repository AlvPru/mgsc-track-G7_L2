package com.example.mgsc.aplicacion;

import com.example.mgsc.domain.Tecnico;
import com.example.mgsc.puertos.RepositorioTecnico;
import com.example.mgsc.puertos.ServicioGestionTecnicos;
import java.util.List;
import java.util.UUID;

public class ServicioTecnicosImpl implements ServicioGestionTecnicos {
    private final RepositorioTecnico repositorio;

    public ServicioTecnicosImpl(RepositorioTecnico repo) {
        this.repositorio = repo;
    }

    @Override
    public Tecnico crearTecnico(String nombre, String especialidad) {
        return null;
    }

    @Override
    public Tecnico activarTecnico(UUID id) {
        return null;
    }

    @Override
    public Tecnico desactivarTecnico(UUID id) {
        return null;
    }

    @Override
    public List<Tecnico> listarTecnicosDisponibles() {
        return null;
    }
}
