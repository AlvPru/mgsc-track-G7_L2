package com.example.mgsc.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TecnicoTest {

    @Test
    void cambiarEstado(){
        Tecnico tecnico = new Tecnico(1, "Carlos", "Plomeria", true);
        tecnico.setActivo(false);
        assertEquals(false, tecnico.isActivo());
    }

    @Test
    void cambiarEspecialidad(){
        Tecnico tecnico = new Tecnico(1, "Carlos", "Plomeria", true);
        tecnico.setEspecialidad("Electricidad");
        assertEquals("Electricidad", tecnico.getEspecialidad());
    }
}
