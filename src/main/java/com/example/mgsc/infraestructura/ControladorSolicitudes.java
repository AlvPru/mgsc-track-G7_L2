package com.example.mgsc.infraestructura;

import com.example.mgsc.puertos.ServicioGestionSolicitudes;
import java.util.UUID;

public class ControladorSolicitudes {
    private final ServicioGestionSolicitudes servicio;

    public ControladorSolicitudes(ServicioGestionSolicitudes servicio) {
        this.servicio = servicio;
    }

    public Object crearSolicitud(UUID clienteId, String descripcion) {
        return null;
    }

    public Object asignarTecnico(UUID solicitudId, UUID tecnicoId) {
        return null;
    }

    public Object cerrarSolicitud(UUID solicitudId) {
        return null;
    }

    public Object listarSolicitudes() {
        return null;
    }
}
