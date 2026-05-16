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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListAppointmentsUseCaseImplTest {

    @Mock
    private AppointmentRepository repository;

    @Mock
    private AppointmentMapper mapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GetLoggedUserEmailPort loggedUserEmailPort;

    @InjectMocks
    private ListAppointmentsUseCaseImpl useCase;

    @Test
    void shouldListOnlyPatientAppointmentsWhenLoggedUserIsPatient() throws BusinessRuleException {
        int page = 0;
        int size = 10;
        Long patientId = 3L;
        OffsetDateTime date = OffsetDateTime.now().plusDays(1);

        User patient = new User(
                patientId,
                "Patient",
                "patient@email.com",
                "123456",
                Role.PATIENT
        );

        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setPatientId(patientId);
        appointment.setDate(date);
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        AppointmentResponseDTO response = new AppointmentResponseDTO(
                1L,
                patientId,
                2L,
                date,
                null,
                AppointmentStatus.SCHEDULED
        );

        when(loggedUserEmailPort.getEmail()).thenReturn("patient@email.com");
        when(userRepository.findByEmail("patient@email.com")).thenReturn(Optional.of(patient));
        when(repository.findByPatientId(patientId, page, size)).thenReturn(List.of(appointment));
        when(mapper.toResponse(appointment)).thenReturn(response);

        List<AppointmentResponseDTO> result = useCase.execute(page, size);

        assertEquals(1, result.size());
        assertEquals(patientId, result.get(0).patientId());

        verify(repository).findByPatientId(patientId, page, size);
        verify(repository, never()).findAll(anyInt(), anyInt());
    }

    @Test
    void shouldListAllAppointmentsWhenLoggedUserIsDoctor() throws BusinessRuleException {
        int page = 0;
        int size = 10;
        OffsetDateTime date = OffsetDateTime.now().plusDays(1);

        User doctor = new User(
                2L,
                "Doctor",
                "doctor@email.com",
                "123456",
                Role.DOCTOR
        );

        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setPatientId(3L);
        appointment.setDoctorId(2L);
        appointment.setDate(date);
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        AppointmentResponseDTO response = new AppointmentResponseDTO(
                1L,
                3L,
                2L,
                date,
                null,
                AppointmentStatus.SCHEDULED
        );

        when(loggedUserEmailPort.getEmail()).thenReturn("doctor@email.com");
        when(userRepository.findByEmail("doctor@email.com")).thenReturn(Optional.of(doctor));
        when(repository.findAll(page, size)).thenReturn(List.of(appointment));
        when(mapper.toResponse(appointment)).thenReturn(response);

        List<AppointmentResponseDTO> result = useCase.execute(page, size);

        assertEquals(1, result.size());

        verify(repository).findAll(page, size);
        verify(repository, never()).findByPatientId(anyLong(), anyInt(), anyInt());
    }

    @Test
    void shouldThrowExceptionWhenLoggedUserDoesNotExist() {
        when(loggedUserEmailPort.getEmail()).thenReturn("unknown@email.com");
        when(userRepository.findByEmail("unknown@email.com")).thenReturn(Optional.empty());

        BusinessRuleException exception = assertThrows(
                BusinessRuleException.class,
                () -> useCase.execute(0, 10)
        );

        assertEquals("user not found", exception.getMessage());

        verify(repository, never()).findAll(anyInt(), anyInt());
        verify(repository, never()).findByPatientId(anyLong(), anyInt(), anyInt());
    }
}