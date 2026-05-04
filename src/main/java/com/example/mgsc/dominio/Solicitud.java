package com.example.mgsc.dominio;

import java.time.LocalDateTime;
public class Solicitud {

    private long id;
    private String descripcion;
    private EstadoSolicitud estado;
    private Cliente clienteAsignado;
    private Tecnico tecnico;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaCierre;

    public Solicitud(String descripcion, Cliente cliente) {
        this.descripcion = descripcion;
        this.estado = EstadoSolicitud.PENDIENTE;
        this.clienteAsignado = cliente;
        this.tecnico = null;
        this.fechaCreacion = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public Cliente getClienteAsignado() {
        return clienteAsignado;
    }

    public void setClienteAsignado(Cliente clienteAsignado) {
        this.clienteAsignado = clienteAsignado;
    }

    public Tecnico getTecnico() {
        if (tecnico == null) {
            return null;
        }
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDateTime fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public void reabrir() {
        if (this.estado != EstadoSolicitud.CERRADA) {
            throw new IllegalStateException("Solo se puede reabrir una solicitud en estado CERRADA");
        }
        this.estado = EstadoSolicitud.EN_PROCESO;
    }

}