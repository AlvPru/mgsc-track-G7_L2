package com.example.mgsc.api.DTOs;

public class TecnicoResponseDTO extends PersonaResponseDTO {
    private String especialidad;
    private boolean activo;

    public TecnicoResponseDTO() {
        super();
    }

    public TecnicoResponseDTO(long id, String nombre, String dni, String especialidad, boolean activo) {
        super(id, nombre, dni);
        this.especialidad = especialidad;
        this.activo = activo;
    }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public boolean getEstado() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
