package com.example.mgsc.unitarios.infrastucture;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.EstadoSolicitud;
import com.example.mgsc.dominio.Solicitud;
import com.example.mgsc.dominio.TipoCliente;
import com.example.mgsc.infrastucture.SolicitudRepositoryMemoria;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unitario")
class SolicitudRepositoryMemoriaTest {

    private SolicitudRepositoryMemoria repo;
    private Cliente clienteStandard;
    private Cliente clientePremium;

    @BeforeEach
    void setUp() {
        repo = SolicitudRepositoryMemoria.getInstance();
        repo.limpiar();
        clienteStandard = new Cliente(1, "Juan", "juan@email.com", TipoCliente.STANDARD);
        clientePremium = new Cliente(2, "Ana", "ana@email.com", TipoCliente.PREMIUM);
    }

    @Test
    void guardarSolicitudStandardYListar() {
        Solicitud solicitud = new Solicitud("Reparar", clienteStandard);
        repo.guardar(solicitud);

        List<Solicitud> lista = repo.listar();
        assertEquals(1, lista.size());
    }

    @Test
    void guardarSolicitudPremiumYListar() {
        Solicitud solicitud = new Solicitud("Urgente", clientePremium);
        repo.guardar(solicitud);

        List<Solicitud> lista = repo.listar();
        assertEquals(1, lista.size());
        assertEquals(TipoCliente.PREMIUM, lista.get(0).getClienteAsignado().getTipo());
    }

    @Test
    void buscarPorIdEnListaStandardDevuelveResultado() {
        Solicitud solicitud = new Solicitud("Reparar", clienteStandard);
        repo.guardar(solicitud);

        Optional<Solicitud> resultado = repo.buscarPorId(solicitud.getId());

        assertTrue(resultado.isPresent());
    }

    @Test
    void buscarPorIdEnListaPremiumDevuelveResultado() {
        Solicitud solicitud = new Solicitud("Urgente", clientePremium);
        repo.guardar(solicitud);

        Optional<Solicitud> resultado = repo.buscarPorId(solicitud.getId());

        assertTrue(resultado.isPresent());
    }

    @Test
    void buscarPorIdInexistenteDevuelveVacio() {
        Optional<Solicitud> resultado = repo.buscarPorId(99999);
        assertFalse(resultado.isPresent());
    }

    @Test
    void modificarSolicitudExistenteDevuelveCero() {
        Solicitud solicitud = new Solicitud("Reparar", clienteStandard);
        repo.guardar(solicitud);
        solicitud.setEstado(EstadoSolicitud.EN_PROCESO);

        int resultado = repo.modificar(solicitud);

        assertEquals(0, resultado);
    }

    @Test
    void modificarSolicitudNoExistenteDevuelveMenosUno() {
        Solicitud fantasma = new Solicitud("No existe", clienteStandard);
        fantasma.setId(99999);

        int resultado = repo.modificar(fantasma);

        assertEquals(-1, resultado);
    }

    @Test
    void limpiarVaciaLaLista() {
        repo.guardar(new Solicitud("S1", clienteStandard));
        repo.guardar(new Solicitud("S2", clientePremium));

        repo.limpiar();

        assertEquals(0, repo.listar().size());
    }

    @Test
    void getProximaSolicitudDevuelvePremiumPrimero() {
        repo.guardar(new Solicitud("Standard", clienteStandard));
        repo.guardar(new Solicitud("Premium", clientePremium));

        Solicitud proxima = repo.getProximaSolicitud();

        assertEquals(TipoCliente.PREMIUM, proxima.getClienteAsignado().getTipo());
    }

    @Test
    void getProximaSolicitudCuandoSoloHayStandard() {
        repo.guardar(new Solicitud("Standard", clienteStandard));

        Solicitud proxima = repo.getProximaSolicitud();

        assertNotNull(proxima);
        assertEquals(TipoCliente.STANDARD, proxima.getClienteAsignado().getTipo());
    }

    @Test
    void getProximaSolicitudCuandoEstaVacioDevuelveNull() {
        Solicitud proxima = repo.getProximaSolicitud();
        assertNull(proxima);
    }
}
