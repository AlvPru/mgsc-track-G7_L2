package com.example.mgsc.aplicacion;

import com.example.mgsc.domain.Solicitud;
import com.example.mgsc.puertos.RepositorioCliente;
import com.example.mgsc.puertos.RepositorioSolicitud;
import com.example.mgsc.puertos.RepositorioTecnico;
import com.example.mgsc.puertos.ServicioGestionSolicitudes;
import java.util.List;
import java.util.UUID;

public class ServicioSolicitudesImpl implements ServicioGestionSolicitudes {
    private final RepositorioSolicitud repoSolicitud;
    private final RepositorioCliente repoCliente;
    private final RepositorioTecnico repoTecnico;

    public ServicioSolicitudesImpl(RepositorioSolicitud repoSolicitud, RepositorioCliente repoCliente, RepositorioTecnico repoTecnico) {
        this.repoSolicitud = repoSolicitud;
        this.repoCliente = repoCliente;
        this.repoTecnico = repoTecnico;
    }

    @Override
    public Solicitud crearSolicitud(UUID clienteId, String descripcion) {
        return null;
    }

    @Override
    public Solicitud asignarTecnicoASolicitud(UUID solicitudId, UUID tecnicoId) {
        return null;
    }

    @Override
    public Solicitud cerrarSolicitud(UUID solicitudId) {
        return null;
    }

    @Override
    public List<Solicitud> listarSolicitudes() {
        return null;
    }
}
