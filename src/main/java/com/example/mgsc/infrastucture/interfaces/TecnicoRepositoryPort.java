package com.example.mgsc.infrastucture.interfaces;

import com.example.mgsc.dominio.Tecnico;
import java.util.List;
import java.util.Optional;

public interface TecnicoRepositoryPort {
    Tecnico guardar(Tecnico tecnico);
    Optional<Tecnico> buscarPorId(long id);
    List<Tecnico> buscarActivo();
    List<Tecnico> listar();
    Optional<Tecnico> buscarPorDni(String dni);
}