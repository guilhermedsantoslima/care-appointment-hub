package br.com.care_appointment_hub.domain.model;

import br.com.care_appointment_hub.domain.enums.AppointmentStatus;
import br.com.care_appointment_hub.domain.exception.BusinessRuleException;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class Appointment {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private OffsetDateTime date;
    private String description;
    private AppointmentStatus status;

    public void reschedule(OffsetDateTime newDate) throws BusinessRuleException {
        OffsetDateTime now = OffsetDateTime.now();

        if (newDate.isBefore(now)){
            throw new BusinessRuleException("Data inválida");
        }
        if (this.status == AppointmentStatus.COMPLETED) {
            throw new BusinessRuleException("Consulta já finalizada");
        }

        if (this.status == AppointmentStatus.CANCELED) {
            throw new BusinessRuleException("Consulta cancelada não pode ser reagendada");
        }
        this.date = newDate;
    }

    public Long getId() {
        return id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}
