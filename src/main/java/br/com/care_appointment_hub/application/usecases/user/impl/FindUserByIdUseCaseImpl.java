package br.com.care_appointment_hub.application.usecases.user.impl;

import br.com.care_appointment_hub.application.dto.UserResponseDTO;
import br.com.care_appointment_hub.application.usecases.user.FindUserByIdUseCase;
import br.com.care_appointment_hub.domain.repository.UserRepository;

public class FindUserByIdUseCaseImpl implements FindUserByIdUseCase {

    private final UserRepository repository;

    public FindUserByIdUseCaseImpl(UserRepository repository) {
        this.repository = repository;
    }
    @Override
    public UserResponseDTO execute(Long id) {
        var user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
