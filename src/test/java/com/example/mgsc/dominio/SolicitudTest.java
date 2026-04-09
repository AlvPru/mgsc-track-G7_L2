package com.example.mgsc.dominio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolicitudTest {

    @Test
    void getTecnicoSinAsignarDebeLanzarIllegalStateException() {
        Cliente cliente = new Cliente(1, "Ana", "ana@email.com", TipoCliente.STANDARD);
        Solicitud solicitud = new Solicitud("Reparacion", cliente);

        assertThrows(IllegalStateException.class, solicitud::getTecnico);
    }

    @Test
    void toStringIncluyeEstadoYCodigoDeSolicitud() {
        Cliente cliente = new Cliente(2, "Luis", "luis@email.com", TipoCliente.PREMIUM);
        Solicitud solicitud = new Solicitud("Inspeccion", cliente);

        String texto = solicitud.toString();

        assertTrue(texto.contains("ABIERTA"));
        assertTrue(texto.contains("Inspeccion"));
    }
}
