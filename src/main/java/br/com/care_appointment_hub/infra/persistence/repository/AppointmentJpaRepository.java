package br.com.care_appointment_hub.infra.persistence.repository;

import br.com.care_appointment_hub.infra.persistence.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentJpaRepository extends JpaRepository<AppointmentEntity, Long> {
}
