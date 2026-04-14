package br.com.care_appointment_hub.application.usecases;

import br.com.care_appointment_hub.application.dto.AppointmentResponseDTO;

import java.util.List;

public interface ListAppointmentsUseCase {

    List<AppointmentResponseDTO> execute(int page, int size);
}
