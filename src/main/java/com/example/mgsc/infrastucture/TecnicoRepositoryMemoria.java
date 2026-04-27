package com.example.mgsc.infrastucture;

import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.infrastucture.interfaces.TecnicoRepositoryPort;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TecnicoRepositoryMemoria implements TecnicoRepositoryPort {
    private final List<Tecnico> tecnicos = new ArrayList<>();

    public TecnicoRepositoryMemoria() {
        //Vacio para que spring pueda inyectar esta clase sin problemas.
    }

    @Override
    public void guardar(Tecnico tecnico) {
        tecnicos.add(tecnico);
    }

    @Override
    public List<Tecnico> buscarActivo() {
        return tecnicos.stream().filter(Tecnico::isActivo).toList();
    }

    @Override
    public List<Tecnico> listar() {
        return new ArrayList<>(tecnicos);
    }
}