package com.example.mgsc.service;

import com.example.mgsc.dominio.Solicitud;
import java.util.List;
import java.util.Optional;

public class SolicitudService {
    private final SolicitudRepositoryPort solicitudRepository;

    public SolicitudService(SolicitudRepositoryPort solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    public void guardar(Solicitud solicitud) {
        solicitudRepository.guardar(solicitud);
    }

    public Optional<Solicitud> buscarPorId(int id) {
        return solicitudRepository.buscarPorId(id);
    }

    public List<Solicitud> listar() {
        return solicitudRepository.listar();
    }

    public Solicitud getProximaSolicitud() {
        return solicitudRepository.getProximaSolicitud();
    }


    public int modificar(Solicitud solicitud) {
        return solicitudRepository.modificar(solicitud);
    }

}