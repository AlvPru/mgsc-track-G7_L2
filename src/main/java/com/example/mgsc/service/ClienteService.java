package com.example.mgsc.service;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.TipoCliente;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return clienteRepository.listar().stream()
                .sorted(Comparator.comparing(Cliente::esPremium).reversed())
                .collect(Collectors.toList());
    }

    public Cliente crearCliente(int id, String nombre, String email, TipoCliente tipo) {
        throw new UnsupportedOperationException("no implementado");
    }

    public Cliente buscarPorIdOrThrow(int id) {
        throw new UnsupportedOperationException("no implementado");
    }
}