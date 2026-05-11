package br.com.care_appointment_hub.infra.graphql;

import br.com.care_appointment_hub.domain.model.Appointment;
import br.com.care_appointment_hub.domain.repository.AppointmentRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
public class AppointmentGraphQLController {

    private final AppointmentRepository repository;

    public AppointmentGraphQLController(AppointmentRepository repository) {
        this.repository = repository;
    }
    @QueryMapping
    public List<Appointment> patientAppointments(@Argument Long patientId){
        return repository.findByPatientId(patientId);
    }

    public List<Appointment> futureAppointments(@Argument Long patientId){
        return repository.findFutureAppointments(patientId, OffsetDateTime.now());
    }
}
