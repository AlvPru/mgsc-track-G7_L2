import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.mgsc.api.SolicitudController;
import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.Solicitud;
import com.example.mgsc.dominio.Tecnico;
import com.example.mgsc.dominio.TipoCliente;
import com.example.mgsc.service.SolicitudService;
import com.example.mgsc.infrastucture.SolicitudRepositoryMemoria;

import java.util.Date;

public class SolicituTest {

    private SolicitudController solicitudController;

    @BeforeEach
    public void setUp() {
        solicituTestRepositoryMemoria.getInstance().limpiar();
        solicitudController = new SolicitudController(solicitudRepositoryMemoria.getInstance());

        Tecnico tecnicoActivo = new Tecnico(1, "Juan", "Perez", "activo");
        Tecnico tecnicoInactivo = new Tecnico(2, "Maria", "Gomez", "inactivo");
        Cliente cliente = new Cliente(1, "Carlos", "Lopez", TipoCliente.NORMAL);
        Solicitud solicitud1 = new Solicitud("prueba", cliente);

        //registrar informacion
        solicitudController.registrarTecnico(tecnicoActivo);
        solicitudController.registrarTecnico(tecnicoInactivo);
        solicitudController.registrarCliente(cliente);
        solicitudController.registrarSolicitud(solicitud);
    }


    @Test
    public void testAsignacionTecnicoActivoEnSolicitud() {
        assertEquals(-1, solicitudController.asignarTecnico(solicitud.getId, tecnicoInactivo.getId()));
        assertEquals(0, solicitudController.asignarTecnico(solicitud1.getId(), tecnicoActivo.getId()));
    }

    @Test
    public void testCierreSolicitudSoloEnProceso() {
        Solicitud solicitudMal = new Solicitud("prueba", cliente);
        solicitudController.registrarSolicitud(solicitudMal);

        solicitudController.asignarTecnico(solicitud1.getId(), tecnicoActivo.getId());
        assertEquals(0, solicitudController.cerrarSolicitud(solicitud.getId()));
        assertEquals(-1, solicitudController.cerrarSolicitud(solicitudMal.getId()));
    }

    @Test
    public void testClientePremiumTienePrioridad() {
        // Verificar que un cliente premium tiene prioridad
        Cliente clientePremium = new Cliente(2, "Ana", "Martinez", TipoCliente.PREMIUM);
        Solicitud solicitudPremium = new Solicitud("prueba premium", clientePremium);
        solicitudController.registrarCliente(clientePremium);

        assertEquals("PREMIUM",solicitudController.getProximaSolicitud().getClienteAsignado().getTipoCliente().name());

        
    }
}
