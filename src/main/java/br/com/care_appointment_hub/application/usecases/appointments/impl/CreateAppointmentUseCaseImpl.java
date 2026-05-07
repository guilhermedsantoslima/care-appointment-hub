package br.com.care_appointment_hub.application.usecases.appointments.impl;

import br.com.care_appointment_hub.application.dto.AppointmentResponseDTO;
import br.com.care_appointment_hub.application.dto.CreateAppointmentRequestDTO;
import br.com.care_appointment_hub.application.mapper.AppointmentMapper;
import br.com.care_appointment_hub.application.port.output.AppointmentEventPublisherPort;
import br.com.care_appointment_hub.application.usecases.appointments.CreateAppointmentUseCase;
import br.com.care_appointment_hub.domain.model.Appointment;
import br.com.care_appointment_hub.domain.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateAppointmentUseCaseImpl implements CreateAppointmentUseCase {

    private final AppointmentRepository repository;
    private final AppointmentEventPublisherPort eventPublisher;
    private final AppointmentMapper mapper;

    public CreateAppointmentUseCaseImpl(AppointmentRepository repository,AppointmentEventPublisherPort eventPublisher,
                                        AppointmentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public AppointmentResponseDTO execute(CreateAppointmentRequestDTO request) {
        Appointment appointment = mapper.toDomain(request);
        Appointment saved = repository.save(appointment);

        eventPublisher.publishCreatedEvent(saved);

        return mapper.toResponse(saved);
    }
}
