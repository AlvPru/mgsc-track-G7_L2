package com.example.mgsc.infrastucture;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.dominio.entidades.TecnicoEntity;
import com.example.mgsc.infrastucture.interfaces.TecnicoRepositoryPort;

@Repository
@Profile("jpa")
public class TecnicoRepositoryAdapter implements TecnicoRepositoryPort {
    private final TecnicoRepositoryJPA tecnicoRepositoryJPA;

    public TecnicoRepositoryAdapter(TecnicoRepositoryJPA tecnicoRepositoryJPA) {
        this.tecnicoRepositoryJPA = tecnicoRepositoryJPA;
    }

    @Override
    public Tecnico guardar(Tecnico tecnico) {
        TecnicoEntity entity = TecnicoEntity.fromDomain(tecnico);
        TecnicoEntity savedEntity = tecnicoRepositoryJPA.save(entity);
        tecnico.setId(savedEntity.getId());
        return tecnico;
    }

    @Override
    public Optional<Tecnico> buscarPorId(long id) {
        return tecnicoRepositoryJPA.findById(id).map(TecnicoEntity::toDomain);
    }

    @Override
    public List<Tecnico> buscarActivo() {
        return tecnicoRepositoryJPA.findByActivoTrue().stream().map(TecnicoEntity::toDomain).toList();
    }

    @Override
    public List<Tecnico> listar() {
        return tecnicoRepositoryJPA.findAll().stream().map(TecnicoEntity::toDomain).toList();
    }

    @Override
    public Optional<Tecnico> buscarPorDni(String dni) {
        return tecnicoRepositoryJPA.findByDni(dni).map(TecnicoEntity::toDomain);
    }

}
