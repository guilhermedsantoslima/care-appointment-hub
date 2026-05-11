package br.com.care_appointment_hub.application.usecases.user.impl;

import br.com.care_appointment_hub.application.dto.CreateUserCommand;
import br.com.care_appointment_hub.application.dto.UserResponseDTO;
import br.com.care_appointment_hub.application.port.output.PasswordEncoderPort;
import br.com.care_appointment_hub.application.usecases.user.CreateUserUseCase;
import br.com.care_appointment_hub.domain.model.User;
import br.com.care_appointment_hub.domain.repository.UserRepository;

public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepository repository;
    private final PasswordEncoderPort passwordEncoder;

    public CreateUserUseCaseImpl(UserRepository repository, PasswordEncoderPort passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserResponseDTO execute(CreateUserCommand userCommand) {
        repository.findByEmail(userCommand.email())
                .ifPresent(u -> {
                    throw new RuntimeException("Email already exists");
                });

        String encryptedPassword = passwordEncoder.encode(userCommand.password());

        User user = new User(
                null,
                userCommand.name(),
                userCommand.email(),
                encryptedPassword,
                userCommand.role()
        );

        var saved = repository.save(user);

        return new UserResponseDTO(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getRole()
        );
    }
}
