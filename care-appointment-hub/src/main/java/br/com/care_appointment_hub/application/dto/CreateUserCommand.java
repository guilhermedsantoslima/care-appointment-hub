package br.com.care_appointment_hub.application.dto;

import br.com.care_appointment_hub.domain.enums.Role;

public record CreateUserCommand(String name,
                                String email,
                                String password,
                                Role role) {
}
