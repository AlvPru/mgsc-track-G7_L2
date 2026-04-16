package com.example.mgsc.infrastucture;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.service.ClienteRepositoryPort;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteRepositoryMemoria implements ClienteRepositoryPort {
    private static ClienteRepositoryMemoria instance=new ClienteRepositoryMemoria();
    private final List<Cliente> clientes;

    private ClienteRepositoryMemoria() {
        clientes = new ArrayList<>();
    }

    public static ClienteRepositoryMemoria getInstance() {
        if (instance == null) {
            instance = new ClienteRepositoryMemoria();
        }
        return instance;
    }

    @Override
    public void guardar(Cliente cliente) {
        clientes.add(cliente);
    }

    @Override
    public Optional<Cliente> buscarPorId(int id) {
        return clientes.stream().filter(c -> c.getId() == id).findFirst();
    }

    @Override
    public List<Cliente> listar() {
        return new ArrayList<>(clientes);
    }

    public void clear() {
        clientes.clear();
    }
}