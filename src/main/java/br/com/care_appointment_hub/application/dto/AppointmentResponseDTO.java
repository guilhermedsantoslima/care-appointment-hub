package br.com.care_appointment_hub.application.dto;

import java.time.LocalDateTime;

public record AppointmentResponseDTO(Long id,
                                     Long patientId,
                                     Long doctorId,
                                     LocalDateTime date,
                                     String description) {
}
