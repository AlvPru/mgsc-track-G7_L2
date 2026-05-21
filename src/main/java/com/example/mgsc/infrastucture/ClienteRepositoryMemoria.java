package com.example.mgsc.infrastucture;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.infrastucture.interfaces.ClienteRepositoryPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("memory")
public class ClienteRepositoryMemoria implements ClienteRepositoryPort {
    private long idCounter = 1;
    private final List<Cliente> clientes = new ArrayList<>();

    public ClienteRepositoryMemoria() {
        // Vacio para que spring pueda inyectar esta clase sin problemas.
    }

    @Override
    public void guardar(Cliente cliente) {
        if(cliente.getId() == -1) {
            cliente.setId(idCounter++);
        }
        clientes.add(cliente);
    }

    @Override
    public Optional<Cliente> buscarPorId(long id) {
        return clientes.stream().filter(c -> c.getId() == id).findFirst();
    }

    @Override
    public List<Cliente> listar() {
        return new ArrayList<>(clientes);
    }

    @Override
    public boolean existe(String dni) {
        return clientes.stream().anyMatch(c -> c.getDni().equals(dni));
    }

    @Override
    public boolean existePorEmail(String email) {
        return clientes.stream().anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public Optional<Cliente> buscarPorDni(String dni) {
        return clientes.stream().filter(c -> c.getDni().equals(dni)).findFirst();
    }
}