package br.com.care_appointment_hub.application.usecases.appointments.impl;

import br.com.care_appointment_hub.application.usecases.appointments.DeleteAppointmentUseCase;
import br.com.care_appointment_hub.domain.enums.AppointmentStatus;
import br.com.care_appointment_hub.domain.exception.BusinessRuleException;
import br.com.care_appointment_hub.domain.model.Appointment;
import br.com.care_appointment_hub.domain.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteAppointmentUseCaseImpl implements DeleteAppointmentUseCase {

    private final AppointmentRepository repository;

    public DeleteAppointmentUseCaseImpl(AppointmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Long id) throws BusinessRuleException {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Appointment not found"));

        appointment.setStatus(AppointmentStatus.CANCELED);

        repository.save(appointment);
    }
}
