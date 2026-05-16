package br.com.care_appointment_hub.application.usecases.appointments.impl;

import br.com.care_appointment_hub.application.dto.AppointmentResponseDTO;
import br.com.care_appointment_hub.application.mapper.AppointmentMapper;
import br.com.care_appointment_hub.application.port.output.GetLoggedUserEmailPort;
import br.com.care_appointment_hub.domain.enums.AppointmentStatus;
import br.com.care_appointment_hub.domain.enums.Role;
import br.com.care_appointment_hub.domain.exception.BusinessRuleException;
import br.com.care_appointment_hub.domain.model.Appointment;
import br.com.care_appointment_hub.domain.model.User;
import br.com.care_appointment_hub.domain.repository.AppointmentRepository;
import br.com.care_appointment_hub.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindAppointmentUseCaseImplTest {

    @Mock
    private AppointmentRepository repository;

    @Mock
    private AppointmentMapper mapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GetLoggedUserEmailPort loggedUserEmailPort;

    @InjectMocks
    private FindAppointmentUseCaseImpl useCase;

    @Test
    void shouldFindAppointmentSuccessfullyForDoctor() throws BusinessRuleException {
        Long appointmentId = 1L;
        OffsetDateTime date = OffsetDateTime.now().plusDays(1);

        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setPatientId(2L);
        appointment.setDoctorId(1L);
        appointment.setDate(date);
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        User doctor = new User(
                1L,
                "Doctor",
                "doctor@email.com",
                "123456",
                Role.DOCTOR
        );

        AppointmentResponseDTO response = new AppointmentResponseDTO(
                appointmentId,
                2L,
                1L,
                date,
                null,
                AppointmentStatus.SCHEDULED
        );

        when(repository.findById(appointmentId)).thenReturn(Optional.of(appointment));
        when(loggedUserEmailPort.getEmail()).thenReturn("doctor@email.com");
        when(userRepository.findByEmail("doctor@email.com")).thenReturn(Optional.of(doctor));
        when(mapper.toResponse(appointment)).thenReturn(response);

        AppointmentResponseDTO result = useCase.findById(appointmentId);

        assertEquals(appointmentId, result.id());

        verify(mapper).toResponse(appointment);
    }

    @Test
    void shouldThrowExceptionWhenAppointmentDoesNotExist() {
        Long appointmentId = 99L;

        when(repository.findById(appointmentId)).thenReturn(Optional.empty());

        BusinessRuleException exception = assertThrows(
                BusinessRuleException.class,
                () -> useCase.findById(appointmentId)
        );

        assertEquals("Appointment not found", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPatientTriesToAccessAnotherPatientAppointment() {
        Long appointmentId = 1L;

        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setPatientId(10L);

        User loggedPatient = new User(
                20L,
                "Patient",
                "patient@email.com",
                "123456",
                Role.PATIENT
        );

        when(repository.findById(appointmentId)).thenReturn(Optional.of(appointment));
        when(loggedUserEmailPort.getEmail()).thenReturn("patient@email.com");
        when(userRepository.findByEmail("patient@email.com")).thenReturn(Optional.of(loggedPatient));

        BusinessRuleException exception = assertThrows(
                BusinessRuleException.class,
                () -> useCase.findById(appointmentId)
        );

        assertEquals("Access denied", exception.getMessage());

        verify(mapper, never()).toResponse(any());
    }
}