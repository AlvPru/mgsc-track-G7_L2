package com.example.mgsc.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Cliente {

    private static final List<Cliente> REPOSITORIO = new ArrayList<>();

    private UUID id;
    private String nombre;
    private String email;
    private TipoCliente tipoCliente;

    public Cliente(UUID id, String nombre, String email, TipoCliente tipoCliente) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.tipoCliente = tipoCliente;
    }

    public static Cliente create(String nombre, String email, TipoCliente tipoCliente) {
        Cliente cliente = new Cliente(UUID.randomUUID(), nombre, email, tipoCliente);
        REPOSITORIO.add(cliente);
        return cliente;
    }

    public static Optional<Cliente> findById(UUID id) {
        return REPOSITORIO.stream()
                .filter(cliente -> cliente.getId().equals(id))
                .findFirst();
    }

    public static List<Cliente> findAll() {
        return new ArrayList<>(REPOSITORIO);
    }

    public static void clearAll() {
        REPOSITORIO.clear();
    }

    public UUID getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public void cambiarEmail(String nuevoEmail) {
        this.email = nuevoEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}