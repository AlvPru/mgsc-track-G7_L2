package com.example.mgsc.unitarios.dominio;

import org.junit.jupiter.api.Test;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.EstadoSolicitud;
import com.example.mgsc.dominio.Solicitud;
import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.dominio.TipoCliente;


import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Tag;
@Tag("unitario")
class SolicitudTest {

    @Test
    void testConstructor() {
        Cliente cliente = new Cliente(1, "Juan", "Perez", TipoCliente.STANDARD);
        Solicitud solicitud = new Solicitud("Reparar fuga", cliente);

        assertEquals("Reparar fuga", solicitud.getDescripcion());
        assertEquals(EstadoSolicitud.PENDIENTE, solicitud.getEstado());
        assertEquals(cliente, solicitud.getClienteAsignado());
        assertNotNull(solicitud.getFechaCreacion());
        assertNull(solicitud.getFechaCierre());
    }


    @Test
    void testSetAndGetTecnico() {
        Cliente cliente = new Cliente(1, "Juan", "Perez", TipoCliente.STANDARD);
        Tecnico tecnico = new Tecnico(1, "Ana", "Electricidad", true);
        Solicitud solicitud = new Solicitud("Reparar fuga", cliente);

        solicitud.setTecnico(tecnico);
        assertEquals(tecnico, solicitud.getTecnico());
    }

    @Test
    void testSettersAndGetters() {
        Cliente cliente = new Cliente(1, "Juan", "Perez", TipoCliente.STANDARD);
        Solicitud solicitud = new Solicitud("Reparar fuga", cliente);

        solicitud.setId(10);
        assertEquals(10, solicitud.getId());

        solicitud.setDescripcion("Nueva descripcion");
        assertEquals("Nueva descripcion", solicitud.getDescripcion());

        solicitud.setEstado(EstadoSolicitud.CERRADA);
        assertEquals(EstadoSolicitud.CERRADA, solicitud.getEstado());

        Cliente nuevoCliente = new Cliente(2, "Maria", "Lopez", TipoCliente.PREMIUM);
        solicitud.setClienteAsignado(nuevoCliente);
        assertEquals(nuevoCliente, solicitud.getClienteAsignado());

        LocalDateTime fechaCreacion = LocalDateTime.now();
        solicitud.setFechaCreacion(fechaCreacion);
        assertEquals(fechaCreacion, solicitud.getFechaCreacion());

        LocalDateTime fechaCierre = LocalDateTime.now();
        solicitud.setFechaCierre(fechaCierre);
        assertEquals(fechaCierre, solicitud.getFechaCierre());
    }

}
