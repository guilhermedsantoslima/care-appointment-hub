package br.com.care_appointment_hub.application.usecases.user;

import br.com.care_appointment_hub.application.dto.UserResponseDTO;

import java.util.List;

public interface FindAllUserUseCase {
    List<UserResponseDTO> execute();
}
