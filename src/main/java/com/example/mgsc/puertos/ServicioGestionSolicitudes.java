package com.example.mgsc.puertos;

import com.example.mgsc.domain.Solicitud;
import java.util.List;
import java.util.UUID;

public interface ServicioGestionSolicitudes {
    Solicitud crearSolicitud(UUID clienteId, String descripcion);

    Solicitud asignarTecnicoASolicitud(UUID solicitudId, UUID tecnicoId);

    Solicitud cerrarSolicitud(UUID solicitudId);

    List<Solicitud> listarSolicitudes();
}
