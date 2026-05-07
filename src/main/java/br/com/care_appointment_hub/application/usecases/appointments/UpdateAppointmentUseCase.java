package br.com.care_appointment_hub.application.usecases.appointments;

import br.com.care_appointment_hub.application.dto.AppointmentResponseDTO;
import br.com.care_appointment_hub.application.dto.UpdateAppointmentRequestDTO;
import br.com.care_appointment_hub.domain.exception.BusinessRuleException;

public interface UpdateAppointmentUseCase {
    AppointmentResponseDTO execute(Long id, UpdateAppointmentRequestDTO request) throws BusinessRuleException;
}
