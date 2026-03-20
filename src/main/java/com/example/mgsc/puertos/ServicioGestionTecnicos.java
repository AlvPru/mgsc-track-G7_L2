package com.example.mgsc.puertos;

import com.example.mgsc.domain.Tecnico;
import java.util.List;
import java.util.UUID;

public interface ServicioGestionTecnicos {
    Tecnico crearTecnico(String nombre, String especialidad);

    Tecnico activarTecnico(UUID id);

    Tecnico desactivarTecnico(UUID id);

    List<Tecnico> listarTecnicosDisponibles();
}
