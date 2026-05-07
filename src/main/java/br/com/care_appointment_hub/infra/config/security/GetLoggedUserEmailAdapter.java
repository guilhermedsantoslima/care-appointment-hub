package br.com.care_appointment_hub.infra.config.security;

import br.com.care_appointment_hub.application.port.output.GetLoggedUserEmailPort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class GetLoggedUserEmailAdapter implements GetLoggedUserEmailPort {

    @Override
    public String getEmail() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
