package br.com.care_appointment_hub.application.usecases.users.impl;

import br.com.care_appointment_hub.application.dto.UserResponseDTO;
import br.com.care_appointment_hub.application.usecases.user.impl.FindUserByIdUseCaseImpl;
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
class FindUserByIdUseCaseImplTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private FindUserByIdUseCaseImpl useCase;

    @Test
    void shouldFindUserByIdSuccessfully() {
        Long userId = 1L;

        User user = new User(
                userId,
                "Doctor",
                "doctor@email.com",
                "password",
                Role.DOCTOR
        );

        when(repository.findById(userId))
                .thenReturn(Optional.of(user));

        UserResponseDTO result = useCase.execute(userId);

        assertEquals(userId, result.id());
        assertEquals("Doctor", result.name());
        assertEquals("doctor@email.com", result.email());
        assertEquals(Role.DOCTOR, result.role());

        verify(repository).findById(userId);
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        Long userId = 99L;

        when(repository.findById(userId))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> useCase.execute(userId)
        );

        assertEquals("User not found", exception.getMessage());
    }
}