package br.com.care_appointment_hub.application.usecases.impl;

import br.com.care_appointment_hub.application.dto.AppointmentResponseDTO;
import br.com.care_appointment_hub.application.mapper.AppointmentMapper;
import br.com.care_appointment_hub.application.usecases.FindAppointmentUseCase;
import br.com.care_appointment_hub.domain.exception.BusinessRuleException;
import br.com.care_appointment_hub.domain.model.Appointment;
import br.com.care_appointment_hub.domain.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

@Service
public class FindAppointmentUseCaseImpl implements FindAppointmentUseCase {
    private final AppointmentRepository repository;
    private final AppointmentMapper mapper;

    public FindAppointmentUseCaseImpl(AppointmentRepository repository, AppointmentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public AppointmentResponseDTO findById(Long id) throws BusinessRuleException {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Consulta não encontrada"));

        return mapper.toResponse(appointment);
    }
}
