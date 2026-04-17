package com.example.mgsc.service;

import com.example.mgsc.dominio.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteRepositoryPort {
    void guardar(Cliente cliente);
    Optional<Cliente> buscarPorId(int id);
    List<Cliente> listar();
    boolean existePorEmail(String email);
}