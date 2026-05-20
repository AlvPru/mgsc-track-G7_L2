package com.example.mgsc.dominio;

public abstract class Persona {
    private long id;
    private String dni;
    private String nombre;

    protected Persona(long id, String nombre,String dni) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
    }
    protected Persona(String nombre, String dni) {
        this.id = -1;
        this.nombre = nombre;
        this.dni = dni;
    }

    public long getId() {
        return id;
    }

    public String getDni() {
        return dni;
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
}