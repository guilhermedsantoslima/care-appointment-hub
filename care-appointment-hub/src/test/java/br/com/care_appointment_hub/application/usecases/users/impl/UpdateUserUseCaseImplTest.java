package br.com.care_appointment_hub.application.usecases.users.impl;

import br.com.care_appointment_hub.application.dto.UpdateUserCommand;
import br.com.care_appointment_hub.application.dto.UserResponseDTO;
import br.com.care_appointment_hub.application.usecases.user.impl.UpdateUserUseCaseImpl;
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
class UpdateUserUseCaseImplTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UpdateUserUseCaseImpl useCase;

    @Test
    void shouldUpdateUserSuccessfully() {
        Long userId = 1L;

        User user = new User(
                userId,
                "Old Name",
                "old@email.com",
                "password",
                Role.DOCTOR
        );

        UpdateUserCommand command = new UpdateUserCommand(
                "New Name",
                "new@email.com"
        );

        User updatedUser = new User(
                userId,
                "New Name",
                "new@email.com",
                "password",
                Role.DOCTOR
        );

        when(repository.findById(userId))
                .thenReturn(Optional.of(user));

        when(repository.findByEmail(command.email()))
                .thenReturn(Optional.empty());

        when(repository.save(user))
                .thenReturn(updatedUser);

        UserResponseDTO result = useCase.execute(userId, command);

        assertEquals(userId, result.id());
        assertEquals("New Name", result.name());
        assertEquals("new@email.com", result.email());
        assertEquals(Role.DOCTOR, result.role());

        verify(repository).save(user);
    }

    @Test
    void shouldUpdateOnlyNameWhenEmailIsNull() {
        Long userId = 1L;

        User user = new User(
                userId,
                "Old Name",
                "doctor@email.com",
                "password",
                Role.DOCTOR
        );

        UpdateUserCommand command = new UpdateUserCommand(
                "New Name",
                null
        );

        User updatedUser = new User(
                userId,
                "New Name",
                "doctor@email.com",
                "password",
                Role.DOCTOR
        );

        when(repository.findById(userId))
                .thenReturn(Optional.of(user));

        when(repository.save(user))
                .thenReturn(updatedUser);

        UserResponseDTO result = useCase.execute(userId, command);

        assertEquals("New Name", result.name());
        assertEquals("doctor@email.com", result.email());

        verify(repository, never()).findByEmail(anyString());
        verify(repository).save(user);
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        Long userId = 99L;

        UpdateUserCommand command = new UpdateUserCommand(
                "New Name",
                "new@email.com"
        );

        when(repository.findById(userId))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> useCase.execute(userId, command)
        );

        assertEquals("User not found", exception.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyInUse() {
        Long userId = 1L;

        User user = new User(
                userId,
                "Doctor",
                "doctor@email.com",
                "password",
                Role.DOCTOR
        );

        User userWithSameEmail = new User(
                2L,
                "Other User",
                "new@email.com",
                "password",
                Role.PATIENT
        );

        UpdateUserCommand command = new UpdateUserCommand(
                "Doctor",
                "new@email.com"
        );

        when(repository.findById(userId))
                .thenReturn(Optional.of(user));

        when(repository.findByEmail(command.email()))
                .thenReturn(Optional.of(userWithSameEmail));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> useCase.execute(userId, command)
        );

        assertEquals("Email already in use", exception.getMessage());

        verify(repository, never()).save(any());
    }
}