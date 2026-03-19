package com.example.mgsc.dominio;

import java.util.UUID;

public class Cliente {
    private UUID id;
    private String nombre;
    private String email;
    private TipoCliente tipo;

    public Cliente(UUID id, String nombre, String email, TipoCliente tipo) {
    }

    public void cambiarEmail(String nuevoEmail) {
    }
}
