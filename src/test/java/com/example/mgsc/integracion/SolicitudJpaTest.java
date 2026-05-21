package com.example.mgsc.integracion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.Solicitud;
import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.dominio.TipoCliente;
import com.example.mgsc.dominio.entidades.ClienteEntity;
import com.example.mgsc.dominio.entidades.SolicitudEntity;
import com.example.mgsc.dominio.entidades.TecnicoEntity;
import com.example.mgsc.infrastucture.ClientRepositoryJPA;
import com.example.mgsc.infrastucture.TecnicoRepositoryJPA;

import org.junit.jupiter.api.Tag;

@Tag("unitario")
@DataJpaTest
@ActiveProfiles("test")
class SolicitudJpaTest {
    @Autowired
    private JpaSolicitudRepository repository;

    @Autowired
    private ClientRepositoryJPA clienteRepository;

    @Autowired
    private TecnicoRepositoryJPA tecnicoRepository;

    @Test
    void guardarYRecuperarSolicitud() {
        Cliente cliente = new Cliente("Juan Pérez", "12345", "juan.perez@example.com", TipoCliente.PREMIUM);
        ClienteEntity clienteGuardado = clienteRepository.save(ClienteEntity.fromDomain(cliente));
        cliente.setId(clienteGuardado.getId());

        Solicitud solicitud = new Solicitud("Esto es de prueba", cliente);
        SolicitudEntity entity = SolicitudEntity.fromDomain(solicitud);
        repository.save(entity);

        Optional<SolicitudEntity> solicitudRecuperada = repository.findById(entity.getId());
        assertEquals("Esto es de prueba", solicitudRecuperada.get().toDomain().getDescripcion());
        assertEquals(clienteGuardado.getId(), solicitudRecuperada.get().toDomain().getClienteAsignado().getId());
        assertEquals("Juan Pérez", solicitudRecuperada.get().toDomain().getClienteAsignado().getNombre());
    }

    @Test
    void guardarYRecuperarSolicitudConTecnico() {
        Cliente cliente = new Cliente("Juan Pérez", "12345", "juan.perez@example.com", TipoCliente.PREMIUM);
        ClienteEntity clienteGuardado = clienteRepository.save(ClienteEntity.fromDomain(cliente));
        cliente.setId(clienteGuardado.getId());

        Tecnico tecnico = new Tecnico("Ana", "67890", "Electricidad", true);
        TecnicoEntity tecnicoGuardado = tecnicoRepository.save(TecnicoEntity.fromDomain(tecnico));
        tecnico.setId(tecnicoGuardado.getId());

        Solicitud solicitud = new Solicitud("Esto es de prueba", cliente);
        solicitud.setTecnico(tecnico);
        SolicitudEntity entity = SolicitudEntity.fromDomain(solicitud);
        repository.save(entity);

        Optional<SolicitudEntity> solicitudRecuperada = repository.findById(entity.getId());
        assertEquals("Ana", solicitudRecuperada.get().toDomain().getTecnico().getNombre());
        assertEquals(tecnicoGuardado.getId(), solicitudRecuperada.get().toDomain().getTecnico().getId());
        assertEquals("Juan Pérez", solicitudRecuperada.get().toDomain().getClienteAsignado().getNombre());
    }
}
