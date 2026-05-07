package br.com.care_appointment_hub.infra.dto;

import jakarta.validation.constraints.Email;

public record UpdateUserRequestDTO(String name,
                                   @Email(message = "Invalid email") String email) {
}
