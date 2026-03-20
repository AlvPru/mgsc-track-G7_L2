package com.example.mgsc.aplicacion;

import com.example.mgsc.domain.Cliente;
import com.example.mgsc.domain.TipoCliente;
import com.example.mgsc.puertos.RepositorioCliente;
import com.example.mgsc.puertos.ServicioGestionClientes;
import java.util.List;
import java.util.UUID;

public class ServicioClientesImpl implements ServicioGestionClientes {
    private final RepositorioCliente repositorio;

    public ServicioClientesImpl(RepositorioCliente repo) {
        this.repositorio = repo;
    }

    @Override
    public Cliente crearCliente(String nombre, String email, TipoCliente tipo) {
        return null;
    }

    @Override
    public Cliente modificarCliente(UUID id, String nuevoNombre, String nuevoEmail) {
        return null;
    }

    @Override
    public Cliente consultarCliente(UUID id) {
        return null;
    }

    @Override
    public List<Cliente> listarClientes() {
        return null;
    }
}
