package br.com.care_appointment_hub.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // 👈 resolve o 403
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 👈 libera tudo temporariamente
                )
                .build();
    }
}
