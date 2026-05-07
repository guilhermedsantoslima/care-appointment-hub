package br.com.care_appointment_hub.application.usecases.user;

import br.com.care_appointment_hub.application.dto.UpdateUserCommand;
import br.com.care_appointment_hub.application.dto.UserResponseDTO;

public interface UpdateUserUseCase {
    UserResponseDTO execute(Long id, UpdateUserCommand userCommand);
}
