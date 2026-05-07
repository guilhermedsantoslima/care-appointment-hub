package br.com.care_appointment_hub.infra.config.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static String getLoggedUserEmail(){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
