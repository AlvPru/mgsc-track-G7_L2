package com.example.mgsc.puertos;

import com.example.mgsc.dominio.Cliente;
import java.util.List;
import java.util.UUID;

public interface RepositorioCliente {
    void guardar(Cliente cliente);

    Cliente obtenerPorId(UUID id);

    List<Cliente> obtenerTodos();

    boolean existePorEmail(String email);
}
