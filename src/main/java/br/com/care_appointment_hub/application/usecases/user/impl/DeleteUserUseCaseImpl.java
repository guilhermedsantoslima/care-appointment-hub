package br.com.care_appointment_hub.application.usecases.user.impl;

import br.com.care_appointment_hub.application.usecases.user.DeleteUserUseCase;
import br.com.care_appointment_hub.domain.repository.UserRepository;

public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

    private final UserRepository repository;

    public DeleteUserUseCaseImpl(UserRepository repository) {
        this.repository = repository;
    }
    @Override
    public void execute(Long id) {
        if (!repository.existsById(id)){
            throw new RuntimeException("User not found");
        }

        repository.deleteById(id);
    }
}
