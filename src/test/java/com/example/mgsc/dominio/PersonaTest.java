package com.example.mgsc.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PersonaTest {

    @Test
    void cambioId() {
        Persona persona = new Persona(1, "Juan") {};
        persona.setId(2);
        assertEquals(2, persona.getId());
    }

    @Test
    void cambioNombre() {
        Persona persona = new Persona(1, "Juan") {};
        persona.setNombre("Ana");
        assertEquals("Ana", persona.getNombre());
    }
}
