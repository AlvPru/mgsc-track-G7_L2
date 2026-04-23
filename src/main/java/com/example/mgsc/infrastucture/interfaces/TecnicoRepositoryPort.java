package com.example.mgsc.infrastucture.interfaces;

import com.example.mgsc.dominio.Tecnico;
import java.util.List;

public interface TecnicoRepositoryPort {
    void guardar(Tecnico tecnico);
    List<Tecnico> buscarActivo();
    List<Tecnico> listar();
}