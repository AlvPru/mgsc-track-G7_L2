package com.example.mgsc.api.DTOs;

public abstract class PersonaResponseDTO {
    private long id;
    private String nombre;
    private String dni;

    protected PersonaResponseDTO() {}

    protected PersonaResponseDTO(long id, String nombre, String dni) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
}
