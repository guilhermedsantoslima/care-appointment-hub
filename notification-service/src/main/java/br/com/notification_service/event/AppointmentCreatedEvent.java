package br.com.notification_service.event;

import java.io.Serializable;
import java.time.OffsetDateTime;

public record AppointmentCreatedEvent(Long appointmentId,
                                      Long patientId,
                                      OffsetDateTime date) implements Serializable {
}
