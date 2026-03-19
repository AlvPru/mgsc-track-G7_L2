package com.example.mgsc.puertos;

import com.example.mgsc.dominio.Tecnico;
import java.util.List;
import java.util.UUID;

public interface RepositorioTecnico {
    void guardar(Tecnico tecnico);

    Tecnico obtenerPorId(UUID id);

    List<Tecnico> obtenerTodosActivos();

    List<Tecnico> obtenerTodos();
}
