package com.example.mgsc.dominio.entidades;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.TipoCliente;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class ClienteEntity extends PersonaEntity {
    
    private String email;

    @Enumerated(EnumType.STRING)
    private TipoCliente tipo;

    public Cliente toDomain(){
        Cliente cliente = new Cliente(super.nombre, super.dni, email, tipo);
        cliente.setId(super.getId());
        cliente.setEmail(this.email);
        cliente.setTipo(this.tipo);
        return cliente;
    }

    public static ClienteEntity fromDomain(Cliente cliente){
        ClienteEntity entity = new ClienteEntity();
        if (cliente.getId() >= 0) {
            entity.id = cliente.getId();
        }
        entity.nombre = cliente.getNombre();
        entity.dni = cliente.getDni();
        entity.email = cliente.getEmail();
        entity.tipo = cliente.getTipo();
        return entity;
    }
}
