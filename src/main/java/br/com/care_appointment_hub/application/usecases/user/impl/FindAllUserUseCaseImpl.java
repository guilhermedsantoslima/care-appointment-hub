package br.com.care_appointment_hub.application.usecases.user.impl;

import br.com.care_appointment_hub.application.dto.UserResponseDTO;
import br.com.care_appointment_hub.application.usecases.user.FindAllUserUseCase;
import br.com.care_appointment_hub.domain.repository.UserRepository;

import java.util.List;

public class FindAllUserUseCaseImpl implements FindAllUserUseCase {

    private final UserRepository repository;

    public FindAllUserUseCaseImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserResponseDTO> execute() {
        return repository.findAll()
                .stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole()
                ))
                .toList();
    }
}
