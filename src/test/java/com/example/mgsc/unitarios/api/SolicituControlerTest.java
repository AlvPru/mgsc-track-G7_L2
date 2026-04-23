package com.example.mgsc.unitarios.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.mgsc.api.SolicitudController;
import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.Solicitud;
import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.dominio.TipoCliente;
import com.example.mgsc.infrastucture.SolicitudRepositoryMemoria;
import com.example.mgsc.service.SolicitudService;

@Tag("unitario")
public class SolicituControlerTest {

    private SolicitudController solicitudController;
    private Solicitud solicitud1;
    private Solicitud solicitud2;

    @BeforeEach
    public void setUp() {
        SolicitudRepositoryMemoria.getInstance().limpiar();
        solicitudController = new SolicitudController(new SolicitudService(SolicitudRepositoryMemoria.getInstance()));

        Cliente cliente = new Cliente(1, "Carlos", "Lopez", TipoCliente.STANDARD);
        solicitud1 = new Solicitud("prueba", cliente);
        solicitud2 = new Solicitud("prueba", cliente);

        // registrar informacion
        solicitudController.registrarSolicitud(solicitud1);
        solicitudController.registrarSolicitud(solicitud2);
    }

    @AfterEach
    public void tearDown() {
        SolicitudRepositoryMemoria.getInstance().limpiar();
    }

    @Test
    public void testAsignacionTecnicoInactivoEnSolicitudDevuelveError() {
        Tecnico tecnicoInactivo = new Tecnico(2, "Maria", "Plomeria", false);
        solicitud1.setId(12234);
        assertEquals(-1, solicitudController.asignarTecnico(solicitud1.getId(), tecnicoInactivo));
    }

    @Test
    public void testAsignacionTecnicoActivoEnSolicitudDevuelveOk() {
        Tecnico tecnicoActivo = new Tecnico(1, "Juan", "Electricidad", true);
        assertEquals(0, solicitudController.asignarTecnico(solicitud1.getId(), tecnicoActivo));
    }

    @Test
    public void testCierreSolicitudEnProcesoDevuelveOk() {
        Tecnico tecnicoActivo = new Tecnico(1, "Juan", "Electricidad", true);
 
        solicitud1.setId(12234);
        solicitudController.asignarTecnico(solicitud1.getId(), tecnicoActivo);
        assertEquals(0, solicitudController.cerrarSolicitud(solicitud1.getId()));
    }

    @Test
    public void testCierreSolicitudNoEnProcesoDevuelveError() {
        Cliente cliente2 = new Cliente(2, "Carlos", "Lopez", TipoCliente.STANDARD);
        Solicitud solicitudMal = new Solicitud("prueba", cliente2);
        
        solicitudController.registrarSolicitud(solicitudMal);
        assertEquals(-1, solicitudController.cerrarSolicitud(solicitudMal.getId()));
    }

    @Test
    public void testClientePremiumTienePrioridad() {
        // Verificar que un cliente premium tiene prioridad
        Cliente clientePremium = new Cliente(2, "Ana", "Martinez", TipoCliente.PREMIUM);
        Solicitud solicitudPremium = new Solicitud("prueba premium", clientePremium);
        solicitudController.registrarSolicitud(solicitudPremium);

        assertEquals("PREMIUM", solicitudController.getProximaSolicitud().getClienteAsignado().getTipo().name());
    }
}
