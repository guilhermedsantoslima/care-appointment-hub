package br.com.care_appointment_hub.infra.dto;


import br.com.care_appointment_hub.domain.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        String name,

        @Email(message = "Email inválido")
        @NotBlank(message = "Email é obrigatório")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        String password,

        Role role
) {
}
