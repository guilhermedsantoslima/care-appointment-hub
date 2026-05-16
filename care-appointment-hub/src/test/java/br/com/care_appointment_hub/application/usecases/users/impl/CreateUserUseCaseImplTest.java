package br.com.care_appointment_hub.application.usecases.users.impl;

import br.com.care_appointment_hub.application.dto.CreateUserCommand;
import br.com.care_appointment_hub.application.dto.UserResponseDTO;
import br.com.care_appointment_hub.application.port.output.PasswordEncoderPort;
import br.com.care_appointment_hub.application.usecases.user.impl.CreateUserUseCaseImpl;
import br.com.care_appointment_hub.domain.enums.Role;
import br.com.care_appointment_hub.domain.model.User;
import br.com.care_appointment_hub.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseImplTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoderPort passwordEncoder;

    @InjectMocks
    private CreateUserUseCaseImpl useCase;

    @Test
    void shouldCreateUserSuccessfully() {
        CreateUserCommand command = new CreateUserCommand(
                "Doctor",
                "doctor@email.com",
                "123456",
                Role.DOCTOR
        );

        User savedUser = new User(
                1L,
                "Doctor",
                "doctor@email.com",
                "encrypted-password",
                Role.DOCTOR
        );

        when(repository.findByEmail(command.email()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(command.password()))
                .thenReturn("encrypted-password");

        when(repository.save(any(User.class)))
                .thenReturn(savedUser);

        UserResponseDTO result = useCase.execute(command);

        assertEquals(1L, result.id());
        assertEquals("Doctor", result.name());
        assertEquals("doctor@email.com", result.email());
        assertEquals(Role.DOCTOR, result.role());

        verify(passwordEncoder).encode("123456");
        verify(repository).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        CreateUserCommand command = new CreateUserCommand(
                "Doctor",
                "doctor@email.com",
                "123456",
                Role.DOCTOR
        );

        User existingUser = new User(
                1L,
                "Doctor",
                "doctor@email.com",
                "password",
                Role.DOCTOR
        );

        when(repository.findByEmail(command.email()))
                .thenReturn(Optional.of(existingUser));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> useCase.execute(command)
        );

        assertEquals("Email already exists", exception.getMessage());

        verify(passwordEncoder, never()).encode(anyString());
        verify(repository, never()).save(any());
    }
}