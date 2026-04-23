package com.example.mgsc.integracion;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mgsc.dominio.entidades.SolicitudEntity;

public interface JpaSolicitudRepository extends JpaRepository<SolicitudEntity, Long> {

}
