package com.example.mgsc.service;

import com.example.mgsc.dominio.Solicitud;
import com.example.mgsc.dominio.Tecnico;
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

    public Solicitud asignarTecnico(int idSolicitud, Tecnico tecnico) {
        Solicitud solicitud = buscarSolicitudOrThrow(idSolicitud);
        solicitud.setTecnico(tecnico);
        solicitud.setEstado("EN PROCESO");
        solicitudRepository.modificar(solicitud);
        return solicitud;
    }

    public Solicitud cambiarEstado(int idSolicitud, String nuevoEstado) {
        Solicitud solicitud = buscarSolicitudOrThrow(idSolicitud);
        solicitud.setEstado(nuevoEstado);
        solicitudRepository.modificar(solicitud);
        return solicitud;
    }

    private Solicitud buscarSolicitudOrThrow(int idSolicitud) {
        return solicitudRepository.buscarPorId(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada: " + idSolicitud));
    }
}