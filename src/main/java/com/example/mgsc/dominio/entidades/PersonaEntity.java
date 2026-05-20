package com.example.mgsc.dominio.entidades;

import jakarta.persistence.*;

@MappedSuperclass
public class PersonaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long id;
    protected String nombre;
    protected String dni;
    
    public Long getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getDni() {
        return dni;
    }
}
