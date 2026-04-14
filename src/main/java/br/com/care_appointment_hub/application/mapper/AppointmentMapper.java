package br.com.care_appointment_hub.application.mapper;

import br.com.care_appointment_hub.application.dto.AppointmentResponseDTO;
import br.com.care_appointment_hub.application.dto.CreateAppointmentRequestDTO;
import br.com.care_appointment_hub.domain.model.Appointment;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    Appointment toDomain(CreateAppointmentRequestDTO requestDTO);
    AppointmentResponseDTO toResponse(Appointment appointment);

    default OffsetDateTime map(LocalDateTime value){
        return value == null ? null: value.atOffset(ZoneOffset.UTC);
    }

    default LocalDateTime map(OffsetDateTime value) {
        return value == null ? null : value.toLocalDateTime();
    }
}
