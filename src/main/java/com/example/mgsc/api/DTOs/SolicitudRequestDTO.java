package com.example.mgsc.api.DTOs;

public class SolicitudRequestDTO {

    private String descripcion;
    private long clienteId;

    public SolicitudRequestDTO() {}

    public SolicitudRequestDTO(String descripcion, long clienteId) {
        this.descripcion = descripcion;
        this.clienteId = clienteId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getClienteId() {
        return clienteId;
    }

    public void setClienteId(long clienteId) {
        this.clienteId = clienteId;
    }
}
