package com.example.mgsc.dominio.entidades;

import com.example.mgsc.dominio.Tecnico;

import jakarta.persistence.*;

@Entity
@Table(name = "tecnicos")
public class TecnicoEntity extends PersonaEntity {

    private String especialidad;
    private boolean activo;

    public Tecnico toDomain(){
        Tecnico tecnico = new Tecnico(super.nombre,super.dni,especialidad,activo);
        tecnico.setId(super.getId());
        tecnico.setEspecialidad(this.especialidad);
        tecnico.setActivo(this.activo);
        return tecnico;
    }

    public static TecnicoEntity fromDomain(Tecnico tecnico){
        TecnicoEntity entity = new TecnicoEntity();
        if (tecnico.getId() >= 0) {
            entity.id = tecnico.getId();
        }
        entity.nombre = tecnico.getNombre();
        entity.especialidad = tecnico.getEspecialidad();
        entity.activo = tecnico.isActivo();
        return entity;
    }
}
