package br.com.care_appointment_hub.application.usecases.user.impl;

import br.com.care_appointment_hub.application.dto.UpdateUserCommand;
import br.com.care_appointment_hub.application.dto.UserResponseDTO;
import br.com.care_appointment_hub.application.usecases.user.UpdateUserUseCase;
import br.com.care_appointment_hub.domain.repository.UserRepository;

public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final UserRepository repository;

    public UpdateUserUseCaseImpl(UserRepository repository) {
        this.repository = repository;
    }
    @Override
    public UserResponseDTO execute(Long id, UpdateUserCommand userCommand) {
        var user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userCommand.email() != null && !userCommand.email().equals(user.getEmail())){
            repository.findByEmail(userCommand.email())
                    .ifPresent(u -> {
                        throw new RuntimeException("Email already in use");
                    });
            user.setEmail(userCommand.email());
        }

        if (userCommand.name() != null){
            user.setName(userCommand.name());
        }

        var userUpdated = repository.save(user);

        return new UserResponseDTO(
                userUpdated.getId(),
                userUpdated.getName(),
                userUpdated.getEmail(),
                userUpdated.getRole()
        );
    }
}
