package com.example.mgsc.infraestructura;

import com.example.mgsc.domain.Cliente;
import com.example.mgsc.puertos.RepositorioCliente;
import java.util.List;
import java.util.UUID;

public class RepositorioClienteJPA implements RepositorioCliente {
    private final JpaClienteRepository springDataRepo;

    public RepositorioClienteJPA(JpaClienteRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public void guardar(Cliente cliente) {
    }

    @Override
    public Cliente obtenerPorId(UUID id) {
        return null;
    }

    @Override
    public List<Cliente> obtenerTodos() {
        return null;
    }

    @Override
    public boolean existePorEmail(String email) {
        return false;
    }
}
