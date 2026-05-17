package com.example.mgsc.unitarios.dominio;

import org.junit.jupiter.api.Test;

import com.example.mgsc.dominio.Cliente;
import com.example.mgsc.dominio.TipoCliente;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Tag;

@Tag("unitario")
class ClienteTest {
    @Test
    void debeCrearClienteStandardConDatosValidos() {
        Cliente cliente = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        assertEquals(-1, cliente.getId());
        assertEquals("Juan", cliente.getNombre());
        assertEquals("juan@email.com", cliente.getEmail());
        assertEquals(TipoCliente.STANDARD, cliente.getTipo());
    }

    @Test
    void debeCrearClientePremiumConDatosValidos() {
        Cliente cliente = new Cliente("Ana", "67890", "ana@email.com", TipoCliente.PREMIUM);
        assertEquals(TipoCliente.PREMIUM, cliente.getTipo());
    }

    @Test
    void esPremiumDebeRetornarTrueParaTipoPremium() {
        Cliente cliente = new Cliente("Ana", "67890", "ana@email.com", TipoCliente.PREMIUM);
        assertTrue(cliente.esPremium());
    }

    @Test
    void esPremiumDebeRetornarFalseParaTipoStandard() {
        Cliente cliente = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        assertFalse(cliente.esPremium());
    }

    @Test
    void cambiarEmailDebeActualizarElEmail() {
        Cliente cliente = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        cliente.cambiarEmail("nuevo@email.com");
        assertEquals("nuevo@email.com", cliente.getEmail());
    }

    @Test
    void setEmailDebeActualizarEmail() {
        Cliente cliente = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        cliente.setEmail("otro@email.com");
        assertEquals("otro@email.com", cliente.getEmail());
    }

    @Test
    void setTipoDebeActualizarTipo() {
        Cliente cliente = new Cliente("Juan", "12345", "juan@email.com", TipoCliente.STANDARD);
        cliente.setTipo(TipoCliente.PREMIUM);
        assertEquals(TipoCliente.PREMIUM, cliente.getTipo());
    }

    @Test
    void setIdDebeActualizarId() {
        Cliente cliente = new Cliente("Juan", "11111", "juan@email.com", TipoCliente.STANDARD);
        cliente.setId(99);
        assertEquals(99, cliente.getId());
    }

    @Test
    void setNombreDebeActualizarNombre() {
        Cliente cliente = new Cliente("Juan", "11111", "juan@email.com", TipoCliente.STANDARD);
        cliente.setNombre("Pedro");
        assertEquals("Pedro", cliente.getNombre());
    }
}
