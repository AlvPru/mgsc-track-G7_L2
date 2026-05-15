package com.example.mgsc.dominio;

public class Tecnico extends Persona {
    private String especialidad;
    private boolean activo;

    public Tecnico(long id, String nombre, String dni, String especialidad, boolean activo) {
        super(id, nombre, dni);
        this.especialidad = especialidad;
        this.activo = activo;
    }

    public Tecnico(String nombre, String dni, String especialidad, boolean activo) {
        super(nombre, dni);
        this.especialidad = especialidad;
        this.activo = activo;
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