package br.com.care_appointment_hub.application.usecases.impl;

import br.com.care_appointment_hub.application.dto.AppointmentResponseDTO;
import br.com.care_appointment_hub.application.mapper.AppointmentMapper;
import br.com.care_appointment_hub.application.usecases.ListAppointmentsUseCase;
import br.com.care_appointment_hub.domain.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ListAppointmentsUseCaseImpl implements ListAppointmentsUseCase {

    private final AppointmentRepository repository;
    private final AppointmentMapper mapper;

    public ListAppointmentsUseCaseImpl(AppointmentRepository repository, AppointmentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<AppointmentResponseDTO> execute(int page, int size) {
        return repository.findAll(page, size)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}
