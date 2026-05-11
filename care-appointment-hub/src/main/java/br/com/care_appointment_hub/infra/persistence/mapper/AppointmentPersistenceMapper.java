package br.com.care_appointment_hub.infra.persistence.mapper;

import br.com.care_appointment_hub.domain.model.Appointment;
import br.com.care_appointment_hub.infra.persistence.entity.AppointmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentPersistenceMapper {

    @Mapping(target = "id", source = "id")
    AppointmentEntity toEntity(Appointment appointment);
    Appointment toDomain(AppointmentEntity entity);
}
