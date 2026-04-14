package br.com.care_appointment_hub.application.usecases.impl;

import br.com.care_appointment_hub.application.dto.AppointmentResponseDTO;
import br.com.care_appointment_hub.application.dto.UpdateAppointmentRequestDTO;
import br.com.care_appointment_hub.application.mapper.AppointmentMapper;
import br.com.care_appointment_hub.application.usecases.UpdateAppointmentUseCase;
import br.com.care_appointment_hub.domain.exception.BusinessRuleException;
import br.com.care_appointment_hub.domain.model.Appointment;
import br.com.care_appointment_hub.domain.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateAppointmentUseCaseImpl implements UpdateAppointmentUseCase {
    private final AppointmentRepository repository;
    private final AppointmentMapper mapper;

    public UpdateAppointmentUseCaseImpl(AppointmentRepository repository, AppointmentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public AppointmentResponseDTO execute(Long id, UpdateAppointmentRequestDTO request) throws BusinessRuleException {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        if (request.date() != null){
            appointment.reschedule(request.date());
        }
        if (request.description() != null){
            appointment.setDescription(request.description());
        }
        if (request.status() != null){
            appointment.setStatus(request.status());
        }

        Appointment appointmentUpdated = repository.save(appointment);
        return mapper.toResponse(appointmentUpdated);
    }
}
