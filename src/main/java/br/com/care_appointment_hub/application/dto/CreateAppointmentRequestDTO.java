package br.com.care_appointment_hub.application.dto;

import java.time.LocalDateTime;

public record CreateAppointmentRequestDTO(Long patientId,
                                          Long doctorId,
                                          LocalDateTime date,
                                          String description) {
}
