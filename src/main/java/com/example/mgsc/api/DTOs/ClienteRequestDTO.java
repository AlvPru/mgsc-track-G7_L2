package com.example.mgsc.api.DTOs;

import com.example.mgsc.dominio.TipoCliente;

public class ClienteRequestDTO extends PersonaRequestDTO {
    private String email;
    private TipoCliente tipo;

    public ClienteRequestDTO() {
        super();
    }

    public ClienteRequestDTO(String nombre, String dni, String email, TipoCliente tipo) {
        super(nombre, dni);
        this.email = email;
        this.tipo = tipo;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public TipoCliente getTipo() { return tipo; }
    public void setTipo(TipoCliente tipo) { this.tipo = tipo; }
}
