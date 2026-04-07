package com.example.mgsc.api;

import com.example.mgsc.service.SolicitudService;
import com.example.mgsc.dominio.Solicitud;

import org.jspecify.annotations.Nullable;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.Tecnico;

public class SolicitudController {
    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    public void registrarSolicitud(Solicitud solicitud) {
        solicitudService.guardar(solicitud);
    }

    public Integer asignarTecnico(int idSolicitud, Tecnico tecnico) {
        if (!tecnico.isActivo()) {
            return -1; // Técnico inactivo
        }
        Solicitud solicitud = solicitudService.buscarPorId(idSolicitud).orElse(null);
        if (solicitud == null) {
            return -1; // Solicitud no encontrada
        }
        solicitud.setTecnico(tecnico);
        solicitud.setEstado("EN PROCESO");
        solicitudService.modificar(solicitud);
        return 0; // Retorna 0 si la modificación fue exitosa
    }

    public Solicitud getProximaSolicitud() {
        return solicitudService.getProximaSolicitud();
    }

    public @Nullable Integer cerrarSolicitud(int i) {
        Solicitud solicitud = solicitudService.buscarPorId(i).orElse(null);
        if (solicitud == null) {
            return -1; // Solicitud no encontrada
        }
        if (!solicitud.getEstado().equals("EN PROCESO")) {
            return -1; // Solo se pueden cerrar solicitudes en proceso
        }
        solicitud.setEstado("CERRADA");
        return solicitudService.modificar(solicitud); // Retorna 0 si la modificación fue exitosa
    }

    public Object listarSolicitudes() {
        return solicitudService.listar();
    }
}