package com.example.mgsc.infrastucture.interfaces;

import com.example.mgsc.dominio.Solicitud;
import java.util.List;
import java.util.Optional;

public interface SolicitudRepositoryPort {
    void guardar(Solicitud solicitud);

    Optional<Solicitud> buscarPorId(long id);

    List<Solicitud> listar();

    int modificar(Solicitud solicitud);

    Solicitud getProximaSolicitud();
}