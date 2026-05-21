package com.example.mgsc.infrastucture;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.example.mgsc.dominio.Solicitud;
import com.example.mgsc.dominio.TipoCliente;
import com.example.mgsc.dominio.entidades.SolicitudEntity;
import com.example.mgsc.infrastucture.interfaces.SolicitudRepositoryPort;

@Repository
@Profile("jpa")
public class SolicitudRepositoryAdapter implements SolicitudRepositoryPort {
    private final SolicitudRepositoryJPA solicitudRepositoryJPA;

    public SolicitudRepositoryAdapter(SolicitudRepositoryJPA solicitudRepositoryJPA) {
        this.solicitudRepositoryJPA = solicitudRepositoryJPA;
    }

    @Override
    public void guardar(Solicitud solicitud) {
        SolicitudEntity entity = SolicitudEntity.fromDomain(solicitud);
        SolicitudEntity savedEntity = solicitudRepositoryJPA.save(entity);
        solicitud.setId(savedEntity.getId());
    }

    @Override
    public Optional<Solicitud> buscarPorId(long id) {
        return solicitudRepositoryJPA.findById(id).map(SolicitudEntity::toDomain);
    }

    @Override
    public List<Solicitud> listar() {
        return solicitudRepositoryJPA.findAll().stream().map(SolicitudEntity::toDomain).toList();
    }

    @Override
    public int modificar(Solicitud solicitud) {
        if (solicitud.getId() < 0 || solicitudRepositoryJPA.findById(solicitud.getId()).isEmpty()) {
            return -1;
        }
        SolicitudEntity entity = SolicitudEntity.fromDomain(solicitud);
        solicitudRepositoryJPA.save(entity);
        return 0;
    }

    @Override
    public Solicitud getProximaSolicitud() {
        List<SolicitudEntity> solicitudes = solicitudRepositoryJPA.findAll(Sort.by(Sort.Direction.ASC, "fechaCreacion"));
        return solicitudes.stream()
                .map(SolicitudEntity::toDomain)
                .filter(s -> s.getClienteAsignado().getTipo() == TipoCliente.PREMIUM)
                .findFirst()
                .orElseGet(() -> solicitudes.stream().map(SolicitudEntity::toDomain).findFirst().orElse(null));
    }

}
