package com.example.mgsc.dominio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

public class SolicitudTest {

    @Test
    public void testConstructor() {
        Cliente cliente = new Cliente(1, "Juan", "Perez", TipoCliente.STANDARD);
        Solicitud solicitud = new Solicitud("Reparar fuga", cliente);

        assertNotNull(solicitud.getId());
        assertEquals("Reparar fuga", solicitud.getDescripcion());
        assertEquals("ABIERTA", solicitud.getEstado());
        assertEquals(cliente, solicitud.getClienteAsignado());
        assertNotNull(solicitud.getFechaCreacion());
        assertNull(solicitud.getFechaCierre());
    }

    @Test
    public void testGetTecnicoSinAsignarLanzaExcepcion() {
        Cliente cliente = new Cliente(1, "Juan", "Perez", TipoCliente.STANDARD);
        Solicitud solicitud = new Solicitud("Reparar fuga", cliente);

        assertThrows(IllegalStateException.class, () -> {
            solicitud.getTecnico();
        });
    }

    @Test
    public void testSetAndGetTecnico() {
        Cliente cliente = new Cliente(1, "Juan", "Perez", TipoCliente.STANDARD);
        Tecnico tecnico = new Tecnico(1, "Ana", "Electricidad", true);
        Solicitud solicitud = new Solicitud("Reparar fuga", cliente);

        solicitud.setTecnico(tecnico);
        assertEquals(tecnico, solicitud.getTecnico());
    }

    @Test
    public void testSettersAndGetters() {
        Cliente cliente = new Cliente(1, "Juan", "Perez", TipoCliente.STANDARD);
        Solicitud solicitud = new Solicitud("Reparar fuga", cliente);

        solicitud.setId(10);
        assertEquals(10, solicitud.getId());

        solicitud.setDescripcion("Nueva descripcion");
        assertEquals("Nueva descripcion", solicitud.getDescripcion());

        solicitud.setEstado("CERRADA");
        assertEquals("CERRADA", solicitud.getEstado());

        Cliente nuevoCliente = new Cliente(2, "Maria", "Lopez", TipoCliente.PREMIUM);
        solicitud.setClienteAsignado(nuevoCliente);
        assertEquals(nuevoCliente, solicitud.getClienteAsignado());

        Date fechaCreacion = new Date();
        solicitud.setFechaCreacion(fechaCreacion);
        assertEquals(fechaCreacion, solicitud.getFechaCreacion());

        Date fechaCierre = new Date();
        solicitud.setFechaCierre(fechaCierre);
        assertEquals(fechaCierre, solicitud.getFechaCierre());
    }

    @Test
    public void testToString() {
        Cliente cliente = new Cliente(1, "Juan", "Perez", TipoCliente.STANDARD);
        Solicitud solicitud = new Solicitud("Reparar fuga", cliente);

        String expected = "Solicitud{id=" + solicitud.getId() + ", descripcion='Reparar fuga', estado='ABIERTA', clienteAsignado=" + cliente + ", tecnico=No asignado, fechaCreacion=" + solicitud.getFechaCreacion() + ", fechaCierre=null}";
        assertEquals(expected, solicitud.toString());
    }

    @Test
    public void testToStringConTecnico() {
        Cliente cliente = new Cliente(1, "Juan", "Perez", TipoCliente.STANDARD);
        Tecnico tecnico = new Tecnico(1, "Ana", "Electricidad", true);
        Solicitud solicitud = new Solicitud("Reparar fuga", cliente);
        solicitud.setTecnico(tecnico);

        String expected = "Solicitud{id=" + solicitud.getId() + ", descripcion='Reparar fuga', estado='ABIERTA', clienteAsignado=" + cliente + ", tecnico=Ana, fechaCreacion=" + solicitud.getFechaCreacion() + ", fechaCierre=null}";
        assertEquals(expected, solicitud.toString());
    }
}
