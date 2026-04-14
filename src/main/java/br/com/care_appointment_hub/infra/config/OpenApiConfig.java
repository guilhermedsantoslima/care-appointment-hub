package br.com.care_appointment_hub.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Care Appointment Hub API")
                        .version("1.0")
                        .description("API de agendamento de consultas e gerenciamento de histórico de pacientes"));
    }
}
