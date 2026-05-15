package com.example.mgsc.api.DTOs;


public class TecnicoResponseDTO {
    private long id;
    private String nombre;
    private String dni;
    private String especialidad;
    private boolean activo;

    public TecnicoResponseDTO() {
    }

    public TecnicoResponseDTO(long id, String nombre, String dni, String especialidad, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
        this.especialidad = especialidad;
        this.activo = activo;
    }

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
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getEspecialidad() {
        return especialidad;
    }
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    public boolean getEstado() {
        return activo;
    }
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
