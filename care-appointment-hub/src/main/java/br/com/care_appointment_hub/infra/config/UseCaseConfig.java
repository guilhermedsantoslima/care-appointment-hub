package br.com.care_appointment_hub.infra.config;

import br.com.care_appointment_hub.application.port.output.PasswordEncoderPort;
import br.com.care_appointment_hub.application.usecases.user.*;
import br.com.care_appointment_hub.application.usecases.user.impl.*;
import br.com.care_appointment_hub.domain.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public CreateUserUseCase createUserUseCase(UserRepository repository, PasswordEncoderPort encoder){
        return new CreateUserUseCaseImpl(repository,encoder);
    }

    @Bean
    public FindAllUserUseCase findAllUsersUseCase(UserRepository repository) {
        return new FindAllUsersUseCaseImpl(repository);
    }

    @Bean
    public FindUserByIdUseCase findUserByIdUseCase(UserRepository repository) {
        return new FindUserByIdUseCaseImpl(repository);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(UserRepository repository) {
        return new UpdateUserUseCaseImpl(repository);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(UserRepository repository) {
        return new DeleteUserUseCaseImpl(repository);
    }
}
