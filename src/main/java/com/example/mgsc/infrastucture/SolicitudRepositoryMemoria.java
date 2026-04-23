package com.example.mgsc.infrastucture;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.Solicitud;
import com.example.mgsc.dominio.TipoCliente;
import com.example.mgsc.infrastucture.interfaces.SolicitudRepositoryPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SolicitudRepositoryMemoria implements SolicitudRepositoryPort {
    private static SolicitudRepositoryMemoria instance;
    private final List<Solicitud> solicitudes = new ArrayList<>();
    private final List<Solicitud> solicitudesPremium = new ArrayList<>();

    private SolicitudRepositoryMemoria() {
    }

    public static SolicitudRepositoryMemoria getInstance() {
        if (instance == null) {
            instance = new SolicitudRepositoryMemoria();
        }
        return instance;
    }

    @Override
    public void guardar(Solicitud solicitud) {
        if (solicitud.getClienteAsignado().getTipo().name().equals("PREMIUM")) {
            solicitudesPremium.add(solicitud);
        } else {
            solicitudes.add(solicitud);
        }
    }

    @Override
    public Optional<Solicitud> buscarPorId(long id) {
        for (Solicitud solicitud : solicitudes) {
            if (solicitud.getId() == id) {
                return Optional.of(solicitud);
            }
        }
        for (Solicitud solicitud : solicitudesPremium) {
            if (solicitud.getId() == id) {
                return Optional.of(solicitud);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Solicitud> listar() {
        List<Solicitud> todasLasSolicitudes = new ArrayList<>(solicitudes);
        todasLasSolicitudes.addAll(solicitudesPremium);
        return todasLasSolicitudes;
    }

    @Override
    public int modificar(Solicitud solicitud) {
        Optional<Solicitud> solicitudExistente = buscarPorId(solicitud.getId());
        if (solicitudExistente.isPresent()) {
            solicitudes.remove(solicitudExistente.get());
            solicitudes.add(solicitud);
            return 0; // Modificación exitosa
        }
        return -1; // Solicitud no encontrada
    }

    @Override
    public Solicitud getProximaSolicitud() {
        if(!solicitudesPremium.isEmpty()){
            return solicitudesPremium.get(0); // No hay solicitudes
        }
        else if(!solicitudes.isEmpty()){
            return solicitudes.get(0); // No hay solicitudes
        }
        else{
            return null;
        }
    }

    public void limpiar() {
        solicitudesPremium.clear();
        solicitudes.clear();
    }
}