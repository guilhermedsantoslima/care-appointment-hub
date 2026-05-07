package br.com.care_appointment_hub.infra.persistence.repository;

import br.com.care_appointment_hub.infra.persistence.entity.AppointmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentJpaRepository extends JpaRepository<AppointmentEntity, Long> {
    Page<AppointmentEntity> findByPatientId(Long patientId, Pageable pageable);
}
