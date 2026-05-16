package br.com.care_appointment_hub.application.usecases.users.impl;

import br.com.care_appointment_hub.application.dto.UserResponseDTO;
import br.com.care_appointment_hub.application.usecases.user.impl.FindAllUsersUseCaseImpl;
import br.com.care_appointment_hub.domain.enums.Role;
import br.com.care_appointment_hub.domain.model.User;
import br.com.care_appointment_hub.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllUsersUseCaseImplTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private FindAllUsersUseCaseImpl useCase;

    @Test
    void shouldFindAllUsersSuccessfully() {

        User doctor = new User(
                1L,
                "Doctor",
                "doctor@email.com",
                "password",
                Role.DOCTOR
        );

        User patient = new User(
                2L,
                "Patient",
                "patient@email.com",
                "password",
                Role.PATIENT
        );

        when(repository.findAll())
                .thenReturn(List.of(doctor, patient));

        List<UserResponseDTO> result = useCase.execute();

        assertEquals(2, result.size());

        assertEquals("Doctor", result.get(0).name());
        assertEquals(Role.DOCTOR, result.get(0).role());

        assertEquals("Patient", result.get(1).name());
        assertEquals(Role.PATIENT, result.get(1).role());

        verify(repository).findAll();
    }
}