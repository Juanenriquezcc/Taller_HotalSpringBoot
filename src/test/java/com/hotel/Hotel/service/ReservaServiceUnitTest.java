package com.hotel.Hotel.service;

import com.hotel.Hotel.domain.Cliente;
import com.hotel.Hotel.domain.Habitacion;
import com.hotel.Hotel.domain.HabitacionEstandar;
import com.hotel.Hotel.domain.RangoFechas;
import com.hotel.Hotel.domain.Reserva;
import com.hotel.Hotel.dto.response.ReservaResponse;
import com.hotel.Hotel.mapper.ReservaMapper;
import com.hotel.Hotel.repository.ClienteRepository;
import com.hotel.Hotel.repository.HabitacionRepository;
import com.hotel.Hotel.repository.ReservaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservaServiceUnitTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private HabitacionRepository habitacionRepository;

    @Mock
    private ReservaMapper reservaMapper;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    void debeObtenerReservaExistenteYMapearLaRespuesta() {
        UUID reservaId = UUID.randomUUID();
        Cliente cliente = new Cliente("Laura Gómez", "laura@correo.com");
        Habitacion habitacion = new HabitacionEstandar("301", 2, 150.0, 1);
        LocalDateTime inicio = LocalDateTime.of(2025, 1, 1, 10, 0);
        LocalDateTime fin = LocalDateTime.of(2025, 1, 4, 10, 0);
        RangoFechas periodo = new RangoFechas(inicio, fin);
        Reserva reserva = new Reserva(cliente, habitacion, periodo);
        ReservaResponse respuestaEsperada = new ReservaResponse(
                reservaId,
                "Laura Gómez",
                "301",
                inicio,
                fin,
                450.0,
                "PENDIENTE");

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reserva));
        when(reservaMapper.toResponse(reserva)).thenReturn(respuestaEsperada);

        ReservaResponse resultado = reservaService.obtenerPorId(reservaId);

        assertEquals(respuestaEsperada, resultado);
        verify(reservaRepository).findById(reservaId);
        verify(reservaMapper).toResponse(reserva);
    }

    @Test
    void debeLanzarExcepcionAlConfirmarReservaInexistente() {
        UUID reservaId = UUID.randomUUID();
        when(reservaRepository.findById(reservaId)).thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> reservaService.confirmar(reservaId)
        );

        verify(reservaRepository).findById(reservaId);
        verify(reservaRepository, never()).save(any(Reserva.class));
        verify(reservaMapper, never()).toResponse(any(Reserva.class));
    }
}
