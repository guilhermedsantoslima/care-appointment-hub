package br.com.care_appointment_hub.infra.exception;

import java.time.LocalDateTime;

public record ApiError(LocalDateTime timeStamp,
                       int status,
                       String error,
                       String message,
                       String path) {
}
