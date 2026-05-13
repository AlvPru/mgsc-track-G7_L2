package com.example.mgsc.service;

import com.example.mgsc.dominio.EstadoSolicitud;
import com.example.mgsc.dominio.Solicitud;
import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.infrastucture.interfaces.SolicitudRepositoryPort;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SolicitudService {
    private final SolicitudRepositoryPort solicitudRepository;

    public SolicitudService(SolicitudRepositoryPort solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    public void guardar(Solicitud solicitud) {
        solicitudRepository.guardar(solicitud);
    }

    public Optional<Solicitud> buscarPorId(long id) {
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

    public int asignarTecnico(long idSolicitud, Tecnico tecnico) {
        Solicitud solicitud = buscarSolicitudOrNull(idSolicitud);
        if (solicitud == null || !tecnico.isActivo()) {
            return -1;
        }
        solicitud.setTecnico(tecnico);
        solicitud.setEstado(EstadoSolicitud.EN_PROCESO);
        return solicitudRepository.modificar(solicitud);
    }

    public Solicitud cambiarEstado(long idSolicitud, EstadoSolicitud nuevoEstado) {
        Solicitud solicitud = buscarSolicitudOrNull(idSolicitud);
        if (solicitud == null) {
            return null;
        }
        solicitud.setEstado(nuevoEstado);
        solicitudRepository.modificar(solicitud);
        return solicitud;
    }

    public Integer cerrarSolicitud(long idSolicitud) {
        Solicitud solicitud = buscarSolicitudOrNull(idSolicitud);
        if (solicitud == null || solicitud.getEstado() != EstadoSolicitud.EN_PROCESO) {
            return -1;
        }
        solicitud.setEstado(EstadoSolicitud.CERRADA);
        return modificar(solicitud);
    }

    public Solicitud reabrirSolicitud(long idSolicitud) {
        Solicitud solicitud = buscarSolicitudOrNull(idSolicitud);
        if (solicitud == null) {
            return null;
        }
        solicitud.reabrir();
        solicitudRepository.modificar(solicitud);
        return solicitud;
    }

    private Solicitud buscarSolicitudOrNull(long idSolicitud) {
        return solicitudRepository.buscarPorId(idSolicitud).orElse(null);
    }

}