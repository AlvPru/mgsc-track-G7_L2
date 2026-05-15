package com.example.mgsc.unitarios.dominio;

import org.junit.jupiter.api.Test;

import com.example.mgsc.dominio.CambioEstado;
import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.EstadoSolicitud;
import com.example.mgsc.dominio.Solicitud;
import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.dominio.TipoCliente;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Tag;
@Tag("unitario")
class SolicitudTest {

    @Test
    void testConstructor() {
        Cliente cliente = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        Solicitud solicitud = new Solicitud("Reparar fuga", cliente);

        assertEquals("Reparar fuga", solicitud.getDescripcion());
        assertEquals(EstadoSolicitud.PENDIENTE, solicitud.getEstado());
        assertEquals(cliente, solicitud.getClienteAsignado());
        assertNotNull(solicitud.getFechaCreacion());
        assertNull(solicitud.getFechaCierre());
    }


    @Test
    void testSetAndGetTecnico() {
        Cliente cliente = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        Tecnico tecnico = new Tecnico(1, "Ana", "67890", "Electricidad", true);
        Solicitud solicitud = new Solicitud("Reparar fuga", cliente);

        solicitud.setTecnico(tecnico);
        assertEquals(tecnico, solicitud.getTecnico());
    }

    @Test
    void testSettersAndGetters() {
        Cliente cliente = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        Solicitud solicitud = new Solicitud("Reparar fuga", cliente);

        solicitud.setId(10);
        assertEquals(10, solicitud.getId());

        solicitud.setDescripcion("Nueva descripcion");
        assertEquals("Nueva descripcion", solicitud.getDescripcion());

        solicitud.setEstado(EstadoSolicitud.CERRADA);
        assertEquals(EstadoSolicitud.CERRADA, solicitud.getEstado());

        Cliente nuevoCliente = new Cliente("Maria", "67890", "maria@email.com", TipoCliente.PREMIUM);
        solicitud.setClienteAsignado(nuevoCliente);
        assertEquals(nuevoCliente, solicitud.getClienteAsignado());

        LocalDateTime fechaCreacion = LocalDateTime.now();
        solicitud.setFechaCreacion(fechaCreacion);
        assertEquals(fechaCreacion, solicitud.getFechaCreacion());

        LocalDateTime fechaCierre = LocalDateTime.now();
        solicitud.setFechaCierre(fechaCierre);
        assertEquals(fechaCierre, solicitud.getFechaCierre());
    }

    @Test
    void cuandoReopenSolicitudCerradaDebeQuedarEnProceso() {
        Cliente cliente = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        Solicitud solicitud = new Solicitud("Reparar fuga", cliente);

        solicitud.setEstado(EstadoSolicitud.EN_PROCESO);
        solicitud.setEstado(EstadoSolicitud.CERRADA);
        solicitud.reabrir();

        assertEquals(EstadoSolicitud.EN_PROCESO, solicitud.getEstado());
    }

    @Test
    void cuandoReopenSolicitudNoClosedDebeLanzarExcepcion() {
        Cliente cliente = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        Solicitud solicitud = new Solicitud("Reparar fuga", cliente);

        solicitud.setEstado(EstadoSolicitud.EN_PROCESO);

        assertThrows(IllegalStateException.class, solicitud::reabrir);
    }

    @Test
    void elHistorialDebeRegistrarTodosLosCambiosDeEstadoEnOrden() {
        Cliente cliente = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        Solicitud solicitud = new Solicitud("Reparar fuga", cliente); // PENDIENTE

        solicitud.setEstado(EstadoSolicitud.EN_PROCESO);
        solicitud.setEstado(EstadoSolicitud.CERRADA);
        solicitud.reabrir();

        List<CambioEstado> historial = solicitud.getHistorial();

        assertEquals(4, historial.size());
        assertEquals(EstadoSolicitud.PENDIENTE,  historial.get(0).getEstado());
        assertEquals(EstadoSolicitud.EN_PROCESO, historial.get(1).getEstado());
        assertEquals(EstadoSolicitud.CERRADA,    historial.get(2).getEstado());
        assertEquals(EstadoSolicitud.EN_PROCESO, historial.get(3).getEstado());
    }

}
