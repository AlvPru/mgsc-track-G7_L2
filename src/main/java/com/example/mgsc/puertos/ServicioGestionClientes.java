package com.example.mgsc.puertos;

import com.example.mgsc.domain.Cliente;
import com.example.mgsc.domain.TipoCliente;
import java.util.List;
import java.util.UUID;

public interface ServicioGestionClientes {
    Cliente crearCliente(String nombre, String email, TipoCliente tipo);

    Cliente modificarCliente(UUID id, String nuevoNombre, String nuevoEmail);

    Cliente consultarCliente(UUID id);

    List<Cliente> listarClientes();
}
