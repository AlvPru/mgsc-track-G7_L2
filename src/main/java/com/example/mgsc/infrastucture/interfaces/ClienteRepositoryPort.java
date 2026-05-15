package com.example.mgsc.infrastucture.interfaces;

import com.example.mgsc.dominio.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteRepositoryPort {
    void guardar(Cliente cliente);
    Optional<Cliente> buscarPorId(long id);
    List<Cliente> listar();
    boolean existe(String dni);
}