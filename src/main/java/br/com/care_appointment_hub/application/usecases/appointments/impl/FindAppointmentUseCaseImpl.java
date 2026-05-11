package br.com.care_appointment_hub.application.usecases.appointments.impl;

import br.com.care_appointment_hub.application.dto.AppointmentResponseDTO;
import br.com.care_appointment_hub.application.mapper.AppointmentMapper;
import br.com.care_appointment_hub.application.port.output.GetLoggedUserEmailPort;
import br.com.care_appointment_hub.application.usecases.appointments.FindAppointmentUseCase;
import br.com.care_appointment_hub.domain.enums.Role;
import br.com.care_appointment_hub.domain.exception.BusinessRuleException;
import br.com.care_appointment_hub.domain.model.Appointment;
import br.com.care_appointment_hub.domain.repository.AppointmentRepository;
import br.com.care_appointment_hub.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class FindAppointmentUseCaseImpl implements FindAppointmentUseCase {
    private final AppointmentRepository repository;
    private final AppointmentMapper mapper;
    private final UserRepository userRepository;
    private final GetLoggedUserEmailPort loggedUserEmailPort;

    public FindAppointmentUseCaseImpl(AppointmentRepository repository, AppointmentMapper mapper, UserRepository userRepository, GetLoggedUserEmailPort loggedUserEmailPort) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.loggedUserEmailPort = loggedUserEmailPort;
    }

    @Override
    public AppointmentResponseDTO findById(Long id) throws BusinessRuleException {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Appointment not found"));

        String email = loggedUserEmailPort.getEmail();

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessRuleException("User not found"));

        if (user.getRole() == Role.PATIENT && !appointment.getPatientId().equals(user.getId())){
            throw new BusinessRuleException("Access denied");
        }

        return mapper.toResponse(appointment);
    }
}
