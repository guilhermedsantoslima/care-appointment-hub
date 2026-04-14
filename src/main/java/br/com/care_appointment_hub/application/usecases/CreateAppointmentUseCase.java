package br.com.care_appointment_hub.application.usecases;

import br.com.care_appointment_hub.application.dto.AppointmentResponseDTO;
import br.com.care_appointment_hub.application.dto.CreateAppointmentRequestDTO;

public interface CreateAppointmentUseCase {
    AppointmentResponseDTO execute(CreateAppointmentRequestDTO request);
}
