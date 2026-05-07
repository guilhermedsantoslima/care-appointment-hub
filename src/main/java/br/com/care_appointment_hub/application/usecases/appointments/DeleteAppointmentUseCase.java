package br.com.care_appointment_hub.application.usecases.appointments;

import br.com.care_appointment_hub.domain.exception.BusinessRuleException;

public interface DeleteAppointmentUseCase {

    void execute(Long id) throws BusinessRuleException;
}
