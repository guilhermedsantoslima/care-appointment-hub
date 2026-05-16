package br.com.care_appointment_hub.application.usecases.appointments.impl;

import br.com.care_appointment_hub.domain.enums.AppointmentStatus;
import br.com.care_appointment_hub.domain.exception.BusinessRuleException;
import br.com.care_appointment_hub.domain.model.Appointment;
import br.com.care_appointment_hub.domain.repository.AppointmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteAppointmentUseCaseImplTest {

    @Mock
    private AppointmentRepository repository;

    @InjectMocks
    private DeleteAppointmentUseCaseImpl useCase;

    @Test
    void shouldCancelAppointmentSuccessfully() throws BusinessRuleException {
        Long appointmentId = 1L;

        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        when(repository.findById(appointmentId))
                .thenReturn(Optional.of(appointment));

        useCase.execute(appointmentId);

        assertEquals(AppointmentStatus.CANCELED, appointment.getStatus());

        verify(repository).save(appointment);
    }

    @Test
    void shouldThrowExceptionWhenAppointmentDoesNotExist() {
        Long appointmentId = 99L;

        when(repository.findById(appointmentId))
                .thenReturn(Optional.empty());

        BusinessRuleException exception = assertThrows(
                BusinessRuleException.class,
                () -> useCase.execute(appointmentId)
        );

        assertEquals("Appointment not found", exception.getMessage());

        verify(repository, never()).save(any());
    }
}