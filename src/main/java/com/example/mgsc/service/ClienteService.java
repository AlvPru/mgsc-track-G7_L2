package com.example.mgsc.service;

import com.example.mgsc.dominio.Cliente;
import java.util.List;
import java.util.Optional;

public class ClienteService {
    private final ClienteRepositoryPort clienteRepository;

    public ClienteService(ClienteRepositoryPort clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public int guardar(Cliente cliente) {
        if (clienteRepository.buscarPorId(cliente.getId()).isPresent()) {
            return -1;
        }
        clienteRepository.guardar(cliente);
        return 0;
    }

    public Optional<Cliente> buscarPorId(int id) {
        return clienteRepository.buscarPorId(id);
    }

    public List<Cliente> listar() {
        return clienteRepository.listar();
    }
}