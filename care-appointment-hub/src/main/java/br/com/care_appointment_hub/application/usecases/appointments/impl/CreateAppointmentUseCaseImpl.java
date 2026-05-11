package br.com.care_appointment_hub.application.usecases.appointments.impl;

import br.com.care_appointment_hub.application.dto.AppointmentResponseDTO;
import br.com.care_appointment_hub.application.dto.CreateAppointmentRequestDTO;
import br.com.care_appointment_hub.application.mapper.AppointmentMapper;
import br.com.care_appointment_hub.application.port.output.AppointmentEventPublisherPort;
import br.com.care_appointment_hub.application.usecases.appointments.CreateAppointmentUseCase;
import br.com.care_appointment_hub.domain.enums.Role;
import br.com.care_appointment_hub.domain.exception.BusinessRuleException;
import br.com.care_appointment_hub.domain.model.Appointment;
import br.com.care_appointment_hub.domain.repository.AppointmentRepository;
import br.com.care_appointment_hub.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Service
public class CreateAppointmentUseCaseImpl implements CreateAppointmentUseCase {

    private final AppointmentRepository repository;
    private final AppointmentEventPublisherPort eventPublisher;
    private final AppointmentMapper mapper;
    private final UserRepository userRepository;

    public CreateAppointmentUseCaseImpl(AppointmentRepository repository, AppointmentEventPublisherPort eventPublisher,
                                        AppointmentMapper mapper, UserRepository userRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
        this.userRepository = userRepository;
    }

    @Override
    public AppointmentResponseDTO execute(CreateAppointmentRequestDTO request) throws BusinessRuleException {
        validateDoctor(request.doctorId());
        validatePatient(request.patientId());
        validateAppointmentDate(request);

        Appointment appointment = mapper.toDomain(request);
        Appointment saved = repository.save(appointment);

        eventPublisher.publishCreatedEvent(saved);

        return mapper.toResponse(saved);
    }

    private void validateDoctor(Long doctorId) throws BusinessRuleException {
        var doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new BusinessRuleException("Doctor not found"));

        if (doctor.getRole() != Role.DOCTOR){
            throw new BusinessRuleException("User is not a doctor");
        }
    }

    private void validatePatient(Long patientId) throws BusinessRuleException {
        var patient = userRepository.findById(patientId)
                .orElseThrow(() ->
                        new BusinessRuleException("Patient not found"));

        if (patient.getRole() != Role.PATIENT) {
            throw new BusinessRuleException("User is not a patient");
        }
    }

    private void validateAppointmentDate(CreateAppointmentRequestDTO request) throws BusinessRuleException {
        if (request.date().isBefore(OffsetDateTime.now())){
            throw new BusinessRuleException("Appointment cannot be in the past");
        }
    }
}
