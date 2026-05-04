package com.example.mgsc.dominio;

import java.time.LocalDateTime;

public class CambioEstado {

    private final EstadoSolicitud estado;
    private final LocalDateTime fecha;

    public CambioEstado(EstadoSolicitud estado) {
        this.estado = estado;
        this.fecha = LocalDateTime.now();
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}
