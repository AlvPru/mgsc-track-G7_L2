package com.example.mgsc.api.DTOs;

public class TecnicoRequestDTO extends PersonaRequestDTO {
    private String especialidad;
    private boolean activo;

    public TecnicoRequestDTO() {
        super();
    }

    public TecnicoRequestDTO(String nombre, String dni, String especialidad, boolean activo) {
        super(nombre, dni);
        this.especialidad = especialidad;
        this.activo = activo;
    }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public boolean getEstado() { return activo; }
    public void setEstado(boolean activo) { this.activo = activo; }
}
