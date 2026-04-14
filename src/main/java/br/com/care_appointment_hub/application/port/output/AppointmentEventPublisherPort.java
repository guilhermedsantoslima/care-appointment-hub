package br.com.care_appointment_hub.application.port.output;

import br.com.care_appointment_hub.domain.model.Appointment;

public interface AppointmentEventPublisherPort {
    void publishCreatedEvent(Appointment appointment);
}
