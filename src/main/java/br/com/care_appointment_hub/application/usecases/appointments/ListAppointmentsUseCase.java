package br.com.care_appointment_hub.application.usecases.appointments;

import br.com.care_appointment_hub.application.dto.AppointmentResponseDTO;
import br.com.care_appointment_hub.domain.exception.BusinessRuleException;

import java.util.List;

public interface ListAppointmentsUseCase {

    List<AppointmentResponseDTO> execute(int page, int size) throws BusinessRuleException;
}
