package com.example.mgsc.dominio.entidades;

import java.time.LocalDateTime;

import com.example.mgsc.dominio.EstadoSolicitud;
import com.example.mgsc.dominio.Solicitud;

import jakarta.persistence.*;

@Entity
@Table(name = "solicitudes")
public class SolicitudEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String descripcion;
    private EstadoSolicitud estado;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity clienteAsignado;
    @ManyToOne(optional = true)
    @JoinColumn(name = "tecnico_id",nullable = true)
    private TecnicoEntity tecnico=null;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaCierre;

    public Solicitud toDomain() {
        Solicitud solicitud = new Solicitud(descripcion, clienteAsignado.toDomain());
        solicitud.setId(id);
        solicitud.setEstado(estado);
        solicitud.setTecnico(tecnico != null ? tecnico.toDomain() : null);
        solicitud.setFechaCreacion(fechaCreacion);
        solicitud.setFechaCierre(fechaCierre);
        return solicitud;
    }

    public static SolicitudEntity fromDomain(Solicitud solicitud) {
        SolicitudEntity entity = new SolicitudEntity();
        entity.descripcion = solicitud.getDescripcion();
        entity.estado = solicitud.getEstado();
        entity.clienteAsignado = ClienteEntity.fromDomain(solicitud.getClienteAsignado());
        entity.tecnico = solicitud.getTecnico() != null ? TecnicoEntity.fromDomain(solicitud.getTecnico()) : null;
        entity.fechaCreacion = solicitud.getFechaCreacion();
        entity.fechaCierre = solicitud.getFechaCierre() != null ? solicitud.getFechaCierre() : null;
        return entity;
    }
    public long getId() {
        return id;
    }
}
