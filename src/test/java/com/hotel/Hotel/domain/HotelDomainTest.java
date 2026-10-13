package com.hotel.Hotel.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HotelDomainTest {

    @Test
    void debeCrearClienteValidoConEstadoInicialCorrecto() {
        Cliente cliente = new Cliente("Ana Pérez", "ana@correo.com");

        assertNotNull(cliente.getId());
        assertTrue(cliente.isActivo());
        assertEquals(0, cliente.getPenalizaciones());
    }

    @Test
    void debeRechazarNombresVaciosYSoloConEspacios() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Cliente("", "ana@correo.com")
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new Cliente("   ", "ana@correo.com")
        );
    }

    @Test
    void debeInactivarClienteDespuesDeTresPenalizaciones() {
        Cliente cliente = new Cliente("Ana Pérez", "ana@correo.com");

        cliente.registrarPenalizacion();
        cliente.registrarPenalizacion();
        cliente.registrarPenalizacion();

        assertEquals(3, cliente.getPenalizaciones());
        assertFalse(cliente.isActivo());
        assertFalse(cliente.puedeRealizarReservas());
    }
}
