package com.example.mgsc.api;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.service.ClienteService;

import java.util.List;
import java.util.Optional;

public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public int registrarCliente(Cliente cliente) {
        return clienteService.guardar(cliente);

    }

    public Optional<Cliente> buscarPorId(int id) {
        return clienteService.buscarPorId(id);
    }

    public List<Cliente> listar() {
        return clienteService.listar();
    }
}