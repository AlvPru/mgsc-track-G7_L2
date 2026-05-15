package com.example.mgsc.api.DTOs;

import com.example.mgsc.dominio.TipoCliente;

public class ClienteRequestDTO {
    private String nombre;
    private String dni;
    private String email;
    private TipoCliente tipo;

    public ClienteRequestDTO() {
    }

    public ClienteRequestDTO(String nombre,String dni, String email, TipoCliente tipo) {
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoCliente getTipo() {
        return tipo;
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo;
    }
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
}
