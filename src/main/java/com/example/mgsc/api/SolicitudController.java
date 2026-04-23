package com.example.mgsc.api;

import com.example.mgsc.service.SolicitudService;
import com.example.mgsc.dominio.Solicitud;

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

    public int asignarTecnico(long idSolicitud, Tecnico tecnico) {
        return solicitudService.asignarTecnico(idSolicitud, tecnico);
    }

    public Solicitud getProximaSolicitud() {
        return solicitudService.getProximaSolicitud();
    }

    public  Integer cerrarSolicitud(long idSolicitud) {
        return solicitudService.cerrarSolicitud(idSolicitud);
    }

    public Object listarSolicitudes() {
        return solicitudService.listar();
    }
}