package com.example.mgsc.infrastucture;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mgsc.dominio.entidades.TecnicoEntity;

@Repository
public interface TecnicoRepositoryJPA extends JpaRepository<TecnicoEntity,Long> {
    List<TecnicoEntity> findByActivoTrue();
    Optional<TecnicoEntity> findByDni(String dni);
}
