package com.example.mgsc.infraestructura;

import com.example.mgsc.dominio.Solicitud;
import com.example.mgsc.puertos.RepositorioSolicitud;
import java.util.List;
import java.util.UUID;

public class RepositorioSolicitudJPA implements RepositorioSolicitud {
    private final JpaSolicitudRepository springDataRepo;

    public RepositorioSolicitudJPA(JpaSolicitudRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public void guardar(Solicitud solicitud) {
    }

    @Override
    public Solicitud obtenerPorId(UUID id) {
        return null;
    }

    @Override
    public List<Solicitud> obtenerTodas() {
        return null;
    }

    @Override
    public List<Solicitud> obtenerPorCliente(UUID clienteId) {
        return null;
    }
}
