package com.example.mgsc.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alvaro Perez Romero
 */
public class Cliente {

    private final UUID id;
    private String nombre;
    private String email;
    private TipoCliente tipoCliente;

    // Mientras no tenemos persistencia, usaremos un repositorio en memoria para almacenar los clientes
    private static final Map<UUID, Cliente> STORE = new ConcurrentHashMap<>();

    private Cliente(UUID id, String nombre, String email, TipoCliente tipoCliente) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.tipoCliente = tipoCliente;
    }

    public static Cliente create(String nombre, String email, TipoCliente tipoCliente) {
        UUID id = UUID.randomUUID();
        Cliente cliente = new Cliente(id, nombre, email, tipoCliente);
        STORE.put(id, cliente);
        return cliente;
    }

    public static Optional<Cliente> findById(UUID id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(STORE.get(id));
    }

    public static List<Cliente> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(STORE.values()));
    }

    public static void clearAll() {
        STORE.clear();
    }

    public UUID getId() {
        return id;
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

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
