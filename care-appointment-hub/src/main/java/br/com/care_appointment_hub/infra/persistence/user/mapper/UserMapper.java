package br.com.care_appointment_hub.infra.persistence.user.mapper;

import br.com.care_appointment_hub.domain.model.User;
import br.com.care_appointment_hub.infra.persistence.user.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(User user);
    User toDomain(UserEntity userEntity);
}
