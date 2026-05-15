package com.example.mgsc.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Objeto de transferencia con los datos necesarios para registrar un técnico")
public class TecnicoRequestDTO {

    @Schema(description = "Nombre completo del técnico", example = "Juan Pérez")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Schema(description = "Área de conocimiento o departamento del técnico", example = "Redes")
    @NotBlank(message = "La especialidad no puede estar vacía")
    private String especialidad;

    @Schema(description = "Indica si el técnico está disponible para recibir asignaciones", example = "true")
    @NotNull(message = "El estado activo/inactivo es obligatorio")
    private Boolean activo;

    // Constructores
    public TecnicoRequestDTO() {}

    public TecnicoRequestDTO(String nombre, String especialidad, Boolean activo) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.activo = activo;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}