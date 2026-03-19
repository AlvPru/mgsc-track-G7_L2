package com.example.mgsc.dominio;

import java.time.LocalDateTime;
import java.util.UUID;

public class Solicitud {
    private UUID id;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private EstadoSolicitud estado;
    private UUID clienteId;
    private UUID tecnicoAsignadoId;
    private LocalDateTime fechaCierre;

    public Solicitud(UUID id, String descripcion, LocalDateTime fechaCreacion, EstadoSolicitud estado, UUID clienteId, UUID tecnicoAsignadoId, LocalDateTime fechaCierre) {
    }

    public void asignarTecnico(UUID tecnicoId, Tecnico tecnico) {
    }

    public void cerrar() {
    }

    public void abrir() {
    }
}
