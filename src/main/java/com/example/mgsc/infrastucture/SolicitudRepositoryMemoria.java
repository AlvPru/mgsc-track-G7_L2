package com.example.mgsc.infrastucture;

import com.example.mgsc.dominio.Solicitud;
import com.example.mgsc.service.SolicitudRepositoryPort;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SolicitudRepositoryMemoria implements SolicitudRepositoryPort {
    private static SolicitudRepositoryMemoria instance;
    private final List<Solicitud> solicitudes = new ArrayList<>();

    private SolicitudRepositoryMemoria() {}

    public static SolicitudRepositoryMemoria getInstance() {
        if (instance == null) {
            instance = new SolicitudRepositoryMemoria();
        }
        return instance;
    }

    @Override
    public void guardar(Solicitud solicitud) {
        solicitudes.add(solicitud);
    }

    @Override
    public Optional<Solicitud> buscarPorId(int id) {
        return solicitudes.stream().filter(s -> s.getId() == id).findFirst();
    }

    @Override
    public List<Solicitud> listar() {
        return new ArrayList<>(solicitudes);
    }
}