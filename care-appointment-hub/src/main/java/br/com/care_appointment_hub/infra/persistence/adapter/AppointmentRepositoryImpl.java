package br.com.care_appointment_hub.infra.persistence.adapter;

import br.com.care_appointment_hub.domain.model.Appointment;
import br.com.care_appointment_hub.domain.repository.AppointmentRepository;
import br.com.care_appointment_hub.infra.persistence.entity.AppointmentEntity;
import br.com.care_appointment_hub.infra.persistence.mapper.AppointmentPersistenceMapper;
import br.com.care_appointment_hub.infra.persistence.repository.AppointmentJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
@Component
public class AppointmentRepositoryImpl implements AppointmentRepository {
    private final AppointmentJpaRepository repository;
    private final AppointmentPersistenceMapper mapper;

    public AppointmentRepositoryImpl(AppointmentJpaRepository repository, AppointmentPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Appointment save(Appointment appointment) {
        AppointmentEntity entity = mapper.toEntity(appointment);
        AppointmentEntity entitySaved = repository.save(entity);

        return mapper.toDomain(entitySaved);
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Appointment> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return repository.findAll(pageable)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Appointment> findByPatientId(Long patientId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return repository.findByPatientId(patientId, pageable)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Appointment> findByPatientId(Long patientId) {
        return repository.findByPatientId(patientId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Appointment> findFutureAppointments(Long patientId, OffsetDateTime now) {
        return repository.findByPatientIdAndDateAfter(patientId, OffsetDateTime.now())
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Appointment> findFutureAppointmentsByPatientId(Long patientId) {
        return repository.findByPatientIdAndDateAfter(patientId, OffsetDateTime.now())
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Boolean existsByDoctorIdAndDate(Long doctorId, OffsetDateTime date) {
        return repository.existsByDoctorIdAndDate(doctorId, date);
    }
}
