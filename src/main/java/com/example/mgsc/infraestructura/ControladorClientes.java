package com.example.mgsc.infraestructura;

import com.example.mgsc.puertos.ServicioGestionClientes;
import java.util.UUID;

public class ControladorClientes {
    private final ServicioGestionClientes servicio;

    public ControladorClientes(ServicioGestionClientes servicio) {
        this.servicio = servicio;
    }

    public Object crearCliente(String nombre, String email, String tipoCliente) {
        return null;
    }

    public Object modificarCliente(UUID id, String nuevoNombre, String nuevoEmail) {
        return null;
    }

    public Object consultarCliente(UUID id) {
        return null;
    }

    public Object listarClientes() {
        return null;
    }
}
