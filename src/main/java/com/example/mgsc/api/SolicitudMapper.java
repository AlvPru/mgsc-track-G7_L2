package com.example.mgsc.api;

import com.example.mgsc.api.DTOs.SolicitudResponseDTO;
import com.example.mgsc.dominio.Solicitud;

public class SolicitudMapper {

    private SolicitudMapper() {}

    public static SolicitudResponseDTO toDTO(Solicitud solicitud) {
        SolicitudResponseDTO dto = new SolicitudResponseDTO();
        dto.setId(solicitud.getId());
        dto.setDescripcion(solicitud.getDescripcion());
        dto.setEstado(solicitud.getEstado().name());
        dto.setClienteId(solicitud.getClienteAsignado().getId());
        dto.setClienteNombre(solicitud.getClienteAsignado().getNombre());
        dto.setFechaCreacion(solicitud.getFechaCreacion());
        dto.setFechaCierre(solicitud.getFechaCierre());
        if (solicitud.getTecnico() != null) {
            dto.setTecnicoId(solicitud.getTecnico().getId());
            dto.setTecnicoNombre(solicitud.getTecnico().getNombre());
        }
        return dto;
    }
}
