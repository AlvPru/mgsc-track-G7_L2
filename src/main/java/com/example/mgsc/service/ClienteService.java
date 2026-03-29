package com.example.mgsc.service;

import com.example.mgsc.dominio.Cliente;
import java.util.List;
import java.util.Optional;

public class ClienteService {
    private final ClienteRepositoryPort clienteRepository;

    public ClienteService(ClienteRepositoryPort clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void guardar(Cliente cliente) {
        clienteRepository.guardar(cliente);
    }

    public Optional<Cliente> buscarPorId(int id) {
        return clienteRepository.buscarPorId(id);
    }

    public List<Cliente> listar() {
        return clienteRepository.listar();
    }
}