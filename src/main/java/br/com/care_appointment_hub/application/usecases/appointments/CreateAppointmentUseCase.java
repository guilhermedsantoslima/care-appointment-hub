package br.com.care_appointment_hub.application.usecases.appointments;

import br.com.care_appointment_hub.application.dto.AppointmentResponseDTO;
import br.com.care_appointment_hub.application.dto.CreateAppointmentRequestDTO;
import br.com.care_appointment_hub.domain.exception.BusinessRuleException;

public interface CreateAppointmentUseCase {
    AppointmentResponseDTO execute(CreateAppointmentRequestDTO request) throws BusinessRuleException;
}
