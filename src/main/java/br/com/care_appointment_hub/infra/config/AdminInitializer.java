package br.com.care_appointment_hub.infra.config;

import br.com.care_appointment_hub.domain.enums.Role;
import br.com.care_appointment_hub.domain.model.User;
import br.com.care_appointment_hub.domain.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    @Bean
    public CommandLineRunner createAdmin(UserRepository repository, PasswordEncoder passwordEncoder){
        return args -> {
            var existing = repository.findByEmail("admin@admin.com");

            if (existing.isEmpty()){
                User admin = new User(
                        null,
                        "Admin",
                        "admin@admin.com",
                        passwordEncoder.encode("123456"),
                        Role.ADMIN
                );

                repository.save(admin);

                System.out.println("ADMIN CRIADO");
            }
        };
    }
}
