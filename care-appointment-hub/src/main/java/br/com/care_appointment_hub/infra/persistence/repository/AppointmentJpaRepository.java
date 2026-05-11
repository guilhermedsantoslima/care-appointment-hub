package br.com.care_appointment_hub.infra.persistence.repository;

import br.com.care_appointment_hub.infra.persistence.entity.AppointmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface AppointmentJpaRepository extends JpaRepository<AppointmentEntity, Long> {
    Page<AppointmentEntity> findByPatientId(Long patientId, Pageable pageable);
    List<AppointmentEntity> findByPatientId(Long patientId);
    List<AppointmentEntity> findByPatientIdAndDateAfter(Long patientId, OffsetDateTime date);
}
