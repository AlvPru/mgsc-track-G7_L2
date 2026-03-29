package com.example.mgsc.infrastucture;

import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.service.TecnicoRepositoryPort;
import java.util.ArrayList;
import java.util.List;

public class TecnicoRepositoryMemoria implements TecnicoRepositoryPort {
    private static TecnicoRepositoryMemoria instance;
    private final List<Tecnico> tecnicos = new ArrayList<>();

    private TecnicoRepositoryMemoria() {}

    public static TecnicoRepositoryMemoria getInstance() {
        if (instance == null) {
            instance = new TecnicoRepositoryMemoria();
        }
        return instance;
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