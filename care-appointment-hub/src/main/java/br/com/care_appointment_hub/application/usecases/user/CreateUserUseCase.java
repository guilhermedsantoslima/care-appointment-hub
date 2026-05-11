package br.com.care_appointment_hub.application.usecases.user;

import br.com.care_appointment_hub.application.dto.CreateUserCommand;
import br.com.care_appointment_hub.application.dto.UserResponseDTO;

public interface CreateUserUseCase {

    UserResponseDTO execute(CreateUserCommand userCommand);
}
