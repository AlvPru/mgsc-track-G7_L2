package com.example.mgsc.infrastucture;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mgsc.dominio.entidades.ClienteEntity;

@Repository
public interface ClientRepositoryJPA extends JpaRepository<ClienteEntity,Long> {
    boolean existsByDni(String dni);
    boolean existsByEmail(String email);
    Optional<ClienteEntity> findByDni(String dni);
}
