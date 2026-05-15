package com.example.mgsc.api.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Datos de una solicitud de servicio")
public class SolicitudResponseDTO {

    @Schema(description = "Identificador único de la solicitud", example = "1")
    private long id;

    @Schema(description = "Descripción del problema", example = "Avería en instalación eléctrica")
    private String descripcion;

    @Schema(description = "Estado actual: PENDIENTE, EN_PROCESO o CERRADA", example = "PENDIENTE")
    private String estado;

    @Schema(description = "ID del cliente asignado", example = "1")
    private long clienteId;

    @Schema(description = "Nombre del cliente", example = "Carlos García")
    private String clienteNombre;

    @Schema(description = "ID del técnico asignado (null si no tiene)", example = "2")
    private Long tecnicoId;

    @Schema(description = "Nombre del técnico asignado (null si no tiene)", example = "Juan Pérez")
    private String tecnicoNombre;

    @Schema(description = "Fecha y hora de creación")
    private LocalDateTime fechaCreacion;

    @Schema(description = "Fecha y hora de cierre (null si no está cerrada)")
    private LocalDateTime fechaCierre;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public long getClienteId() { return clienteId; }
    public void setClienteId(long clienteId) { this.clienteId = clienteId; }

    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }

    public Long getTecnicoId() { return tecnicoId; }
    public void setTecnicoId(Long tecnicoId) { this.tecnicoId = tecnicoId; }

    public String getTecnicoNombre() { return tecnicoNombre; }
    public void setTecnicoNombre(String tecnicoNombre) { this.tecnicoNombre = tecnicoNombre; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(LocalDateTime fechaCierre) { this.fechaCierre = fechaCierre; }
}
