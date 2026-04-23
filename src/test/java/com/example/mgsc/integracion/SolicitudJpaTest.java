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
import com.example.mgsc.dominio.entidades.SolicitudEntity;

import org.junit.jupiter.api.Tag;

@Tag("unitario")
@DataJpaTest
@ActiveProfiles("test")
class SolicitudJpaTest {
    @Autowired
    private JpaSolicitudRepository repository;

    @Test
    void guardarYRecuperarSolicitud() {
        Cliente cliente = new Cliente(666, "Juan Pérez", "juan.perez@example.com", TipoCliente.PREMIUM);
        Solicitud solicitud = new Solicitud("Esto es de prueba", cliente);
        SolicitudEntity entity = SolicitudEntity.fromDomain(solicitud);
        repository.save(entity);

        Optional<SolicitudEntity> solicitudRecuperada = repository.findById(entity.getId());
        assertEquals("Esto es de prueba", solicitudRecuperada.get().toDomain().getDescripcion());
    }

    @Test
    void guardarYRecuperarSolicitudConTecnico() {
        Cliente cliente = new Cliente(666, "Juan Pérez", "juan.perez@example.com", TipoCliente.PREMIUM);
        Solicitud solicitud = new Solicitud("Esto es de prueba", cliente);
        Tecnico tecnico = new Tecnico(1, "Ana", "Electricidad", true);
        solicitud.setTecnico(tecnico);
        SolicitudEntity entity = SolicitudEntity.fromDomain(solicitud);
        repository.save(entity);

        Optional<SolicitudEntity> solicitudRecuperada = repository.findById(entity.getId());
        assertEquals("Ana", solicitudRecuperada.get().toDomain().getTecnico().getNombre());
    }
}
