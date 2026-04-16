package com.example.mgsc.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.Solicitud;
import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.dominio.TipoCliente;
import com.example.mgsc.infrastucture.SolicitudRepositoryMemoria;
import com.example.mgsc.service.SolicitudService;

 class SolicituControlerTest {

    private SolicitudController solicitudController;

    @BeforeEach
     void setUp() {
        solicitudController = new SolicitudController(new SolicitudService(SolicitudRepositoryMemoria.getInstance()));

        Cliente cliente = new Cliente(1, "Carlos", "Lopez", TipoCliente.STANDARD);
        Solicitud solicitud1 = new Solicitud("prueba", cliente);
        Solicitud solicitud2 = new Solicitud("prueba", cliente);

        // registrar informacion
        solicitudController.registrarSolicitud(solicitud1);
        solicitudController.registrarSolicitud(solicitud2);
    }

    @AfterEach
     void tearDown() {
        // Limpiar datos después de cada prueba
        SolicitudRepositoryMemoria.getInstance().listar().clear();
    }

    @Test
     void testAsignacionTecnicoInactivoEnSolicitudDevuelveError() {
        Tecnico tecnicoInactivo = new Tecnico(2, "Maria", "Plomeria", false);
        assertEquals(-1, solicitudController.asignarTecnico(1, tecnicoInactivo));
    }

    @Test
     void testAsignacionTecnicoActivoEnSolicitudDevuelveOk() {
        Tecnico tecnicoActivo = new Tecnico(1, "Juan", "Electricidad", true);
        assertEquals(0, solicitudController.asignarTecnico(1, tecnicoActivo));
    }

    @Test
    void testCierreSolicitudEnProcesoDevuelveOk() {
        Tecnico tecnicoActivo = new Tecnico(1, "Juan", "Electricidad", true);

        solicitudController.asignarTecnico(1, tecnicoActivo);
        assertEquals(0, solicitudController.cerrarSolicitud(1));
    }

    @Test
    void testCierreSolicitudNoEnProcesoDevuelveError() {
        Cliente cliente2 = new Cliente(2, "Carlos", "Lopez", TipoCliente.STANDARD);
        Solicitud solicitudMal = new Solicitud("prueba", cliente2);
        
        solicitudController.registrarSolicitud(solicitudMal);
        assertEquals(-1, solicitudController.cerrarSolicitud(solicitudMal.getId()));
    }

    @Test
    void testClientePremiumTienePrioridad() {
        // Verificar que un cliente premium tiene prioridad
        Cliente clientePremium = new Cliente(2, "Ana", "Martinez", TipoCliente.PREMIUM);
        Solicitud solicitudPremium = new Solicitud("prueba premium", clientePremium);
        solicitudController.registrarSolicitud(solicitudPremium);

        assertEquals("PREMIUM", solicitudController.getProximaSolicitud().getClienteAsignado().getTipo().name());
    }
}
