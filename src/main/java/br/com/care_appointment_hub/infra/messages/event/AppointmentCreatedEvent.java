package br.com.care_appointment_hub.infra.messages.event;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record AppointmentCreatedEvent(Long appointmentId,
                                      Long patientId,
                                      OffsetDateTime date) implements Serializable {
}
