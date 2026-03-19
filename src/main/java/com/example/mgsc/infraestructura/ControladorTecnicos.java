package com.example.mgsc.infraestructura;

import com.example.mgsc.puertos.ServicioGestionTecnicos;
import java.util.UUID;

public class ControladorTecnicos {
    private final ServicioGestionTecnicos servicio;

    public ControladorTecnicos(ServicioGestionTecnicos servicio) {
        this.servicio = servicio;
    }

    public Object crearTecnico(String nombre, String especialidad) {
        return null;
    }

    public Object activarTecnico(UUID id) {
        return null;
    }

    public Object desactivarTecnico(UUID id) {
        return null;
    }

    public Object listarTecnicosDisponibles() {
        return null;
    }
}
