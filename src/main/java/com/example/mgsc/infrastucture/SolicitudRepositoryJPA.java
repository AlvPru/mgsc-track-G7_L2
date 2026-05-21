package com.example.mgsc.infrastucture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mgsc.dominio.entidades.SolicitudEntity;

@Repository
public interface SolicitudRepositoryJPA extends JpaRepository<SolicitudEntity, Long> {

}
