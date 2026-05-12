package com.example.mgsc.api.DTOs;

import com.example.mgsc.dominio.TipoCliente;

public class ClienteResponseDTO {
    private long id;
    private String nombre;
    private String email;
    private TipoCliente tipo;

    public ClienteResponseDTO() {
    }

    public ClienteResponseDTO(long id, String nombre, String email, TipoCliente tipo) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.tipo = tipo;
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
}
