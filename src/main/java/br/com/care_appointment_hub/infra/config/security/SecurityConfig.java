package br.com.care_appointment_hub.infra.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/**",
                                "/v3/api-docs/**",
                                 "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // 👑 ADMIN - gerenciamento de usuários
                        .requestMatchers(HttpMethod.POST, "/users/admin")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST, "/users/doctor")
                        .hasRole("ADMIN")

                        // 👨‍⚕️ ADMIN e DOCTOR podem criar enfermeiros
                        .requestMatchers(HttpMethod.POST, "/users/nurse")
                        .hasAnyRole("ADMIN", "DOCTOR")

                        // 👨‍⚕️🩺 todos da área médica podem criar pacientes
                        .requestMatchers(HttpMethod.POST, "/users/patient")
                        .hasAnyRole("ADMIN", "DOCTOR", "NURSE")

                        // 👀 listar usuários
                        .requestMatchers(HttpMethod.GET, "/users/**")
                        .hasAnyRole("ADMIN", "DOCTOR")

                        // ✏️ atualização → só ADMIN
                        .requestMatchers(HttpMethod.PUT, "/users/**")
                        .hasRole("ADMIN")

                        // ❌ delete → só ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/users/**")
                        .hasRole("ADMIN")

                        // 📅 consultas
                        .requestMatchers(HttpMethod.POST, "/appointments")
                        .hasAnyRole("ADMIN", "DOCTOR", "NURSE")

                        .requestMatchers(HttpMethod.PUT, "/appointments/**")
                        .hasAnyRole("ADMIN", "DOCTOR")

                        .requestMatchers(HttpMethod.DELETE, "/appointments/**")
                        .hasAnyRole("ADMIN", "DOCTOR")

                        .requestMatchers(HttpMethod.GET, "/appointments/**")
                        .hasAnyRole("ADMIN", "DOCTOR", "NURSE", "PATIENT")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
