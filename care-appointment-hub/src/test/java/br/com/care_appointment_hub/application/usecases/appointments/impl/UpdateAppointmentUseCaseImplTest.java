package br.com.care_appointment_hub.application.usecases.appointments.impl;

import br.com.care_appointment_hub.application.dto.UpdateAppointmentRequestDTO;
import br.com.care_appointment_hub.application.mapper.AppointmentMapper;
import br.com.care_appointment_hub.application.port.output.AppointmentEventPublisherPort;
import br.com.care_appointment_hub.domain.enums.AppointmentStatus;
import br.com.care_appointment_hub.domain.exception.BusinessRuleException;
import br.com.care_appointment_hub.domain.model.Appointment;
import br.com.care_appointment_hub.domain.repository.AppointmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateAppointmentUseCaseImplTest {

    @Mock
    private AppointmentRepository repository;

    @Mock
    private AppointmentMapper mapper;

    @Mock
    private AppointmentEventPublisherPort eventPublisher;

    @InjectMocks
    private UpdateAppointmentUseCaseImpl useCase;

    @Test
    void shouldThrowExceptionWhenTryingToCancelAppointmentByUpdate() {
        Long appointmentId = 1L;

        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        UpdateAppointmentRequestDTO request = new UpdateAppointmentRequestDTO(
                null,
                null,
                AppointmentStatus.CANCELED
        );

        when(repository.findById(appointmentId))
                .thenReturn(Optional.of(appointment));

        assertThrows(BusinessRuleException.class,
                () -> useCase.execute(appointmentId, request));

        verify(repository, never()).save(any());
        verify(eventPublisher, never()).publishUpdatedEvent(any());
    }

    @Test
    void shouldUpdateDescriptionSuccessfully() throws BusinessRuleException {
        Long appointmentId = 1L;

        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setDate(OffsetDateTime.now().plusDays(1));

        UpdateAppointmentRequestDTO request = new UpdateAppointmentRequestDTO(
                null,
                "Updated description",
                null
        );

        when(repository.findById(appointmentId))
                .thenReturn(Optional.of(appointment));

        when(repository.save(appointment))
                .thenReturn(appointment);

        useCase.execute(appointmentId, request);

        verify(repository).save(appointment);
        verify(eventPublisher).publishUpdatedEvent(appointment);
    }
}