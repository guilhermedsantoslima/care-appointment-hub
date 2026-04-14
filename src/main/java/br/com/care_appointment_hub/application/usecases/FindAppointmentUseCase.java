package br.com.care_appointment_hub.application.usecases;

import br.com.care_appointment_hub.application.dto.AppointmentResponseDTO;
import br.com.care_appointment_hub.domain.exception.BusinessRuleException;

public interface FindAppointmentUseCase {
    AppointmentResponseDTO findById(Long id) throws BusinessRuleException;
}
