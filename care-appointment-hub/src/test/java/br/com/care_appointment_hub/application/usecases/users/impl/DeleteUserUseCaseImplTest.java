package br.com.care_appointment_hub.application.usecases.users.impl;

import br.com.care_appointment_hub.application.usecases.user.impl.DeleteUserUseCaseImpl;
import br.com.care_appointment_hub.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseImplTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private DeleteUserUseCaseImpl useCase;

    @Test
    void shouldDeleteUserSuccessfully() {
        Long userId = 1L;

        when(repository.existsById(userId))
                .thenReturn(true);

        useCase.execute(userId);

        verify(repository).deleteById(userId);
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        Long userId = 99L;

        when(repository.existsById(userId))
                .thenReturn(false);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> useCase.execute(userId)
        );

        assertEquals("User not found", exception.getMessage());

        verify(repository, never()).deleteById(anyLong());
    }
}