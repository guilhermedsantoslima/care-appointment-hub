package br.com.care_appointment_hub.application.dto;

import br.com.care_appointment_hub.domain.enums.AppointmentStatus;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record UpdateAppointmentRequestDTO(OffsetDateTime date,
                                          String description,
                                          AppointmentStatus status) {
}
