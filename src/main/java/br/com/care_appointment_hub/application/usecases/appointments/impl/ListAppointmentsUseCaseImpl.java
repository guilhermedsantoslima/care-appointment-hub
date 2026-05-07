package br.com.care_appointment_hub.application.usecases.appointments.impl;

import br.com.care_appointment_hub.application.dto.AppointmentResponseDTO;
import br.com.care_appointment_hub.application.mapper.AppointmentMapper;
import br.com.care_appointment_hub.application.port.output.GetLoggedUserEmailPort;
import br.com.care_appointment_hub.application.usecases.appointments.ListAppointmentsUseCase;
import br.com.care_appointment_hub.domain.enums.Role;
import br.com.care_appointment_hub.domain.exception.BusinessRuleException;
import br.com.care_appointment_hub.domain.repository.AppointmentRepository;
import br.com.care_appointment_hub.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ListAppointmentsUseCaseImpl implements ListAppointmentsUseCase {

    private final AppointmentRepository repository;
    private final AppointmentMapper mapper;
    private final UserRepository userRepository;
    private final GetLoggedUserEmailPort loggedUserEmailPort;

    public ListAppointmentsUseCaseImpl(AppointmentRepository repository, AppointmentMapper mapper, UserRepository userRepository,
                                       GetLoggedUserEmailPort loggedUserEmailPort) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.loggedUserEmailPort = loggedUserEmailPort;
    }

    @Override
    public List<AppointmentResponseDTO> execute(int page, int size) throws BusinessRuleException {
        String email = loggedUserEmailPort.getEmail();

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessRuleException("user not found"));

        var appointments = (user.getRole() == Role.PATIENT)
                ? repository.findByPatientId(user.getId(), page, size)
                : repository.findAll(page, size);

        return appointments.stream()
                .map(mapper::toResponse)
                .toList();
    }
}
