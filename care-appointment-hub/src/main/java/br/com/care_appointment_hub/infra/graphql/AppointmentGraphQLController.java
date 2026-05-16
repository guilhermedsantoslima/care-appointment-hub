package br.com.care_appointment_hub.infra.graphql;

import br.com.care_appointment_hub.application.dto.AppointmentResponseDTO;
import br.com.care_appointment_hub.application.mapper.AppointmentMapper;
import br.com.care_appointment_hub.application.port.output.GetLoggedUserEmailPort;
import br.com.care_appointment_hub.domain.enums.Role;
import br.com.care_appointment_hub.domain.exception.BusinessRuleException;
import br.com.care_appointment_hub.domain.repository.AppointmentRepository;
import br.com.care_appointment_hub.domain.repository.UserRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AppointmentGraphQLController {

    private final AppointmentRepository repository;
    private final AppointmentMapper mapper;
    private final UserRepository userRepository;
    private final GetLoggedUserEmailPort loggedUserEmailPort;

    public AppointmentGraphQLController(AppointmentRepository repository, AppointmentMapper mapper, UserRepository userRepository, GetLoggedUserEmailPort loggedUserEmailPort) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.loggedUserEmailPort = loggedUserEmailPort;
    }
    @QueryMapping
    public List<AppointmentResponseDTO> patientAppointments(@Argument Long patientId) throws BusinessRuleException {
        validatePatientAccess(patientId);

        return repository.findByPatientId(patientId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @QueryMapping
    public List<AppointmentResponseDTO> futureAppointments(@Argument Long patientId) throws BusinessRuleException {
        validatePatientAccess(patientId);

        return repository.findFutureAppointmentsByPatientId(patientId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    private void validatePatientAccess(Long patientId) throws BusinessRuleException {
        String email = loggedUserEmailPort.getEmail();

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessRuleException("User not found"));

        if (user.getRole() == Role.PATIENT && !user.getId().equals(patientId)) {
            throw new BusinessRuleException("Access denied");
        }
    }
}
