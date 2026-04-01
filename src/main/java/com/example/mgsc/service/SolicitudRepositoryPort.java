package com.example.mgsc.service;

import com.example.mgsc.dominio.Solicitud;
import java.util.List;
import java.util.Optional;

public interface SolicitudRepositoryPort {
    void guardar(Solicitud solicitud);
    Optional<Solicitud> buscarPorId(int id);
    List<Solicitud> listar();
} 