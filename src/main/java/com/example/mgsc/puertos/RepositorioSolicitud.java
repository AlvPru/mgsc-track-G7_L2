package com.example.mgsc.puertos;

import com.example.mgsc.dominio.Solicitud;
import java.util.List;
import java.util.UUID;

public interface RepositorioSolicitud {
    void guardar(Solicitud solicitud);

    Solicitud obtenerPorId(UUID id);

    List<Solicitud> obtenerTodas();

    List<Solicitud> obtenerPorCliente(UUID clienteId);
}
