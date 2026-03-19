package com.example.mgsc.infraestructura;

import java.time.LocalDateTime;
import java.util.UUID;

public class SolicitudEntity {
    private UUID id;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private String estado;
    private ClienteEntity cliente;
    private TecnicoEntity tecnicoAsignado;
    private LocalDateTime fechaCierre;

    public SolicitudEntity(UUID id, String descripcion, LocalDateTime fechaCreacion, String estado, ClienteEntity cliente, TecnicoEntity tecnicoAsignado, LocalDateTime fechaCierre) {
    }
}
