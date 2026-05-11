package br.com.care_appointment_hub.infra.graphql;

import br.com.care_appointment_hub.application.dto.AppointmentResponseDTO;
import br.com.care_appointment_hub.application.mapper.AppointmentMapper;
import br.com.care_appointment_hub.domain.model.Appointment;
import br.com.care_appointment_hub.domain.repository.AppointmentRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;

@Controller
public class AppointmentGraphQLController {

    private final AppointmentRepository repository;
    private final AppointmentMapper mapper;

    public AppointmentGraphQLController(AppointmentRepository repository, AppointmentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    @QueryMapping
    public List<AppointmentResponseDTO> patientAppointments(@Argument Long patientId) {

        return repository.findByPatientId(patientId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @QueryMapping
    public List<AppointmentResponseDTO> futureAppointments(@Argument Long patientId) {

        return repository.findFutureAppointmentsByPatientId(patientId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}
