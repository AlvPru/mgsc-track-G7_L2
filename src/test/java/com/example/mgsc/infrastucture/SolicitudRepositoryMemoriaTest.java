package com.example.mgsc.infrastucture;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.Solicitud;
import com.example.mgsc.dominio.TipoCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SolicitudRepositoryMemoriaTest {

    private SolicitudRepositoryMemoria repository;

    @BeforeEach
    void setUp() {
        repository = SolicitudRepositoryMemoria.getInstance();
        repository.limpiar();
    }

    @Test
    void guardarPremiumDebePreferirLaListaPremium() {
        Cliente clientePremium = new Cliente(1, "Ana", "ana@email.com", TipoCliente.PREMIUM);
        Solicitud solicitudPremium = new Solicitud("Solicitud premium", clientePremium);

        repository.guardar(solicitudPremium);

        assertEquals(solicitudPremium, repository.getProximaSolicitud());
        assertTrue(repository.buscarPorId(solicitudPremium.getId()).isPresent());
    }

    @Test
    void modificarSolicitudExistenteDebeRetornarCero() {
        Cliente cliente = new Cliente(2, "Juan", "juan@email.com", TipoCliente.STANDARD);
        Solicitud solicitud = new Solicitud("Solicitud abierta", cliente);
        repository.guardar(solicitud);

        solicitud.setEstado("EN PROCESO");
        int resultado = repository.modificar(solicitud);

        assertEquals(0, resultado);
        assertEquals("EN PROCESO", repository.buscarPorId(solicitud.getId()).get().getEstado());
    }

    @Test
    void modificarSolicitudNoExistenteDebeRetornarMenosUno() {
        Cliente cliente = new Cliente(3, "Luis", "luis@email.com", TipoCliente.STANDARD);
        Solicitud solicitud = new Solicitud("Solicitud inexistente", cliente);

        int resultado = repository.modificar(solicitud);

        assertEquals(-1, resultado);
    }

    @Test
    void getProximaSolicitudDevuelveNullSiNoHaySolicitudes() {
        assertNull(repository.getProximaSolicitud());
    }

    @Test
    void listarDebeRetornarTodasLasSolicitudes() {
        Cliente cliente1 = new Cliente(4, "Carlos", "carlos@email.com", TipoCliente.STANDARD);
        Cliente cliente2 = new Cliente(5, "Luisa", "luisa@email.com", TipoCliente.PREMIUM);
        Solicitud solicitud1 = new Solicitud("Primera", cliente1);
        Solicitud solicitud2 = new Solicitud("Segunda", cliente2);

        repository.guardar(solicitud1);
        repository.guardar(solicitud2);

        List<Solicitud> todas = repository.listar();

        assertEquals(2, todas.size());
        assertTrue(todas.contains(solicitud1));
        assertTrue(todas.contains(solicitud2));
    }
}
