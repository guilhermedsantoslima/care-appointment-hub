package br.com.care_appointment_hub.domain.repository;

import br.com.care_appointment_hub.domain.model.Appointment;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {
    Appointment save(Appointment appointment);
    Optional<Appointment> findById(Long id);
    List<Appointment> findAll(int page, int size);
    List<Appointment> findByPatientId(Long patientId, int page, int size);
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findFutureAppointments(Long patientId, OffsetDateTime now);
    List<Appointment> findFutureAppointmentsByPatientId(Long patientId);
    Boolean existsByDoctorIdAndDate(Long doctorId, OffsetDateTime date);
}
