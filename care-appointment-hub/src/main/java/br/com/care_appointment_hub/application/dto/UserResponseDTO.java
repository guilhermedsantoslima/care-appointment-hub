package br.com.care_appointment_hub.application.dto;

import br.com.care_appointment_hub.domain.enums.Role;

public record UserResponseDTO (Long id,
                               String name,
                               String email,
                               Role role){
}
