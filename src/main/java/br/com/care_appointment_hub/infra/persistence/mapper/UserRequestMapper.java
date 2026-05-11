package br.com.care_appointment_hub.infra.persistence.mapper;

import br.com.care_appointment_hub.application.dto.CreateUserCommand;
import br.com.care_appointment_hub.application.dto.UpdateUserCommand;
import br.com.care_appointment_hub.infra.dto.CreateUserRequestDTO;
import br.com.care_appointment_hub.infra.dto.UpdateUserRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {

    CreateUserCommand toCommand(CreateUserRequestDTO request);
    UpdateUserCommand toCommand(UpdateUserRequestDTO request);
}
