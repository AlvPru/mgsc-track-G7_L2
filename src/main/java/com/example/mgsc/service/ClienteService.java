package com.example.mgsc.service;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.TipoCliente;
import com.example.mgsc.infrastucture.interfaces.ClienteRepositoryPort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
@Service
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
        List<Cliente> clientes = new ArrayList<>(clienteRepository.listar());
        clientes.sort(Comparator.comparing(Cliente::esPremium).reversed());
        return clientes;
    }

    public Cliente crearCliente(String nombre, String dni, String email, TipoCliente tipo) {
        if (clienteRepository.existe(dni)) {
            throw new IllegalArgumentException("Ya existe un cliente con DNI: " + dni);
        }
        if (clienteRepository.existePorEmail(email)) {
            throw new IllegalArgumentException("Ya existe un cliente con email: " + email);
        }
        Cliente cliente = new Cliente(nombre, dni, email, tipo);
        clienteRepository.guardar(cliente);
        return cliente;
    }

    public Cliente buscarPorIdOrThrow(long id) {
        return clienteRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + id));
    }

    public Optional<Cliente> buscarPorDni(String dni) {
        return clienteRepository.buscarPorDni(dni);
    }
}