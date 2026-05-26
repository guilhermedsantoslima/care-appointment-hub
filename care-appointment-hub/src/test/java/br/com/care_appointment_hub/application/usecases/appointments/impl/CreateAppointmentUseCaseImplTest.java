package br.com.care_appointment_hub.application.usecases.appointments.impl;

import br.com.care_appointment_hub.application.dto.AppointmentResponseDTO;
import br.com.care_appointment_hub.application.dto.CreateAppointmentRequestDTO;
import br.com.care_appointment_hub.application.mapper.AppointmentMapper;
import br.com.care_appointment_hub.application.port.output.AppointmentEventPublisherPort;
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
class CreateAppointmentUseCaseImplTest {

    @Mock
    private AppointmentRepository repository;

    @Mock
    private AppointmentEventPublisherPort eventPublisher;

    @Mock
    private AppointmentMapper mapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateAppointmentUseCaseImpl useCase;

    @Test
    void shouldCreateAppointmentSuccessfully() throws BusinessRuleException {
        Long doctorId = 1L;
        Long patientId = 2L;
        OffsetDateTime date = OffsetDateTime.now().plusDays(1);

        User doctor = new User(
                doctorId,
                "Doctor",
                "doctor@email.com",
                "123456",
                Role.DOCTOR
        );

        User patient = new User(
                patientId,
                "Patient",
                "patient@email.com",
                "123456",
                Role.PATIENT
        );

        CreateAppointmentRequestDTO request = new CreateAppointmentRequestDTO(
                patientId,
                doctorId,
                date,
                "Consulta inicial"
        );

        Appointment appointment = new Appointment();
        appointment.setPatientId(patientId);
        appointment.setDoctorId(doctorId);
        appointment.setDate(date);
        appointment.setDescription("Consulta inicial");
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        Appointment saved = new Appointment();
        saved.setId(10L);
        saved.setPatientId(patientId);
        saved.setDoctorId(doctorId);
        saved.setDate(date);
        saved.setDescription("Consulta inicial");
        saved.setStatus(AppointmentStatus.SCHEDULED);

        AppointmentResponseDTO response = new AppointmentResponseDTO(
                10L,
                patientId,
                doctorId,
                date,
                "Consulta inicial",
                AppointmentStatus.SCHEDULED
        );

        when(userRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(userRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(mapper.toDomain(request)).thenReturn(appointment);
        when(repository.save(appointment)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        AppointmentResponseDTO result = useCase.execute(request);

        assertEquals(10L, result.id());
        assertEquals(AppointmentStatus.SCHEDULED, result.status());

        verify(repository).save(appointment);
        verify(eventPublisher).publishCreatedEvent(saved);
    }

    @Test
    void shouldThrowExceptionWhenDoctorDoesNotExist() {
        Long doctorId = 99L;
        Long patientId = 2L;

        CreateAppointmentRequestDTO request = new CreateAppointmentRequestDTO(
                patientId,
                doctorId,
                OffsetDateTime.now().plusDays(1),
                "Consulta"
        );

        when(userRepository.findById(doctorId)).thenReturn(Optional.empty());

        BusinessRuleException exception = assertThrows(
                BusinessRuleException.class,
                () -> useCase.execute(request)
        );

        assertEquals("Doctor not found", exception.getMessage());

        verify(repository, never()).save(any());
        verify(eventPublisher, never()).publishCreatedEvent(any());
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotDoctor() {
        Long doctorId = 1L;
        Long patientId = 2L;

        User patient = new User(
                patientId,
                "Patient",
                "patient@email.com",
                "123456",
                Role.PATIENT
        );

        CreateAppointmentRequestDTO request = new CreateAppointmentRequestDTO(
                patientId,
                doctorId,
                OffsetDateTime.now().plusDays(1),
                "Consulta"
        );

        when(userRepository.findById(doctorId)).thenReturn(Optional.of(patient));

        BusinessRuleException exception = assertThrows(
                BusinessRuleException.class,
                () -> useCase.execute(request)
        );

        assertEquals("User is not a doctor", exception.getMessage());

        verify(repository, never()).save(any());
        verify(eventPublisher, never()).publishCreatedEvent(any());
    }

    @Test
    void shouldThrowExceptionWhenPatientDoesNotExist() {
        Long doctorId = 1L;
        Long patientId = 99L;

        User doctor = new User(
                doctorId,
                "Doctor",
                "doctor@email.com",
                "123456",
                Role.DOCTOR
        );

        CreateAppointmentRequestDTO request = new CreateAppointmentRequestDTO(
                patientId,
                doctorId,
                OffsetDateTime.now().plusDays(1),
                "Consulta"
        );

        when(userRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(userRepository.findById(patientId)).thenReturn(Optional.empty());

        BusinessRuleException exception = assertThrows(
                BusinessRuleException.class,
                () -> useCase.execute(request)
        );

        assertEquals("Patient not found", exception.getMessage());

        verify(repository, never()).save(any());
        verify(eventPublisher, never()).publishCreatedEvent(any());
    }

    @Test
    void shouldThrowExceptionWhenAppointmentDateIsInPast() {
        Long doctorId = 1L;
        Long patientId = 2L;

        User doctor = new User(
                doctorId,
                "Doctor",
                "doctor@email.com",
                "123456",
                Role.DOCTOR
        );

        User patient = new User(
                patientId,
                "Patient",
                "patient@email.com",
                "123456",
                Role.PATIENT
        );

        CreateAppointmentRequestDTO request = new CreateAppointmentRequestDTO(
                patientId,
                doctorId,
                OffsetDateTime.now().minusDays(1),
                "Consulta"
        );

        when(userRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(userRepository.findById(patientId)).thenReturn(Optional.of(patient));

        BusinessRuleException exception = assertThrows(
                BusinessRuleException.class,
                () -> useCase.execute(request)
        );

        assertEquals("Appointment cannot be in the past", exception.getMessage());

        verify(repository, never()).save(any());
        verify(eventPublisher, never()).publishCreatedEvent(any());
    }

    @Test
    void shouldThrowExceptionWhenDoctorAlreadyHasAppointmentAtSameDate() {

        OffsetDateTime date = OffsetDateTime.now().plusDays(1);

        Long patientId = 1L;
        Long doctorId = 2L;

        CreateAppointmentRequestDTO request =
                new CreateAppointmentRequestDTO(
                        patientId,
                        doctorId,
                        date,
                        "Appointment"
                );

        User patient = new User(
                patientId,
                "Patient",
                "patient@email.com",
                "123",
                Role.PATIENT
        );

        User doctor = new User(
                doctorId,
                "Doctor",
                "doctor@email.com",
                "123",
                Role.DOCTOR
        );

        when(userRepository.findById(doctorId))
                .thenReturn(Optional.of(doctor));

        when(userRepository.findById(patientId))
                .thenReturn(Optional.of(patient));

        when(repository.existsByDoctorIdAndDate(doctorId, date))
                .thenReturn(true);

        BusinessRuleException exception = assertThrows(
                BusinessRuleException.class,
                () -> useCase.execute(request)
        );

        assertEquals(
                "Doctor already has an appointment at this date and time",
                exception.getMessage()
        );

        verify(repository, never()).save(any());
        verify(eventPublisher, never()).publishCreatedEvent(any());
    }
}