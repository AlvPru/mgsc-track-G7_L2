package com.example.mgsc.infrastucture;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.entidades.ClienteEntity;
import com.example.mgsc.infrastucture.interfaces.ClienteRepositoryPort;

@Repository
@Profile("jpa")
public class ClientRepositoryAdapter implements ClienteRepositoryPort {
    private final ClientRepositoryJPA clientRepositoryJPA;

    public ClientRepositoryAdapter(ClientRepositoryJPA clientRepositoryJPA) {
        this.clientRepositoryJPA = clientRepositoryJPA;
    }

    @Override
    public void guardar(Cliente cliente) {
        ClienteEntity entity = ClienteEntity.fromDomain(cliente);
        ClienteEntity savedEntity = clientRepositoryJPA.save(entity);
        cliente.setId(savedEntity.getId());
    }

    @Override
    public Optional<Cliente> buscarPorId(long id) {
        return clientRepositoryJPA.findById(id).map(ClienteEntity::toDomain);
    }

    @Override
    public List<Cliente> listar() {
        return clientRepositoryJPA.findAll().stream().map(ClienteEntity::toDomain).toList();
    }

    @Override
    public boolean existe(String dni) {
        return clientRepositoryJPA.existsByDni(dni);
    }

    @Override
    public boolean existePorEmail(String email) {
        return clientRepositoryJPA.existsByEmail(email);
    }

    @Override
    public Optional<Cliente> buscarPorDni(String dni) {
        return clientRepositoryJPA.findByDni(dni).map(ClienteEntity::toDomain);
    }

}
