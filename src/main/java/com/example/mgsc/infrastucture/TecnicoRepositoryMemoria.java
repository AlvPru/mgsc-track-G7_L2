package com.example.mgsc.infrastucture;

import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.infrastucture.interfaces.TecnicoRepositoryPort;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("memory")
public class TecnicoRepositoryMemoria implements TecnicoRepositoryPort {
    private long idCounter = 1;
    private final List<Tecnico> tecnicos = new ArrayList<>();

    public TecnicoRepositoryMemoria() {
        // Vacio para que spring pueda inyectar esta clase sin problemas.
    }

    @Override
    public Tecnico guardar(Tecnico tecnico) {
        if (tecnico.getId() == -1) {
            tecnico.setId(idCounter++);
        }
        tecnicos.add(tecnico);
        return tecnico;
    }

    @Override
    public List<Tecnico> buscarActivo() {
        return tecnicos.stream().filter(Tecnico::isActivo).toList();
    }

    @Override
    public List<Tecnico> listar() {
        return new ArrayList<>(tecnicos);
    }

    @Override
    public Optional<Tecnico> buscarPorDni(String dni) {
        return tecnicos.stream().filter(t -> t.getDni().equals(dni)).findFirst();
    }

    @Override
    public Optional<Tecnico> buscarPorId(long id) {
        return tecnicos.stream().filter(t -> t.getId() == id).findFirst();
    }
}