package com.example.mgsc.api.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos necesarios para crear una solicitud")
public class SolicitudRequestDTO {

    @Schema(description = "Descripción del problema o incidencia", example = "Avería en instalación eléctrica")
    private String descripcion;

    @Schema(description = "ID del cliente que realiza la solicitud", example = "1")
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
