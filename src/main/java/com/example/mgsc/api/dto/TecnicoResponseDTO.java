package com.example.mgsc.api.dto;

public class TecnicoResponseDTO {

    private long id;
    private String nombre;
    private String especialidad;
    private boolean activo;

    // Constructores
    public TecnicoResponseDTO() {}

    public TecnicoResponseDTO(long id, String nombre, String especialidad, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.activo = activo;
    }

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}