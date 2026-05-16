package br.com.care_appointment_hub.domain.model;
import static org.junit.jupiter.api.Assertions.*;
import br.com.care_appointment_hub.domain.enums.AppointmentStatus;
import br.com.care_appointment_hub.domain.exception.BusinessRuleException;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

public class AppointmentTest {
    @Test
    void shouldRescheduleAppointmentSuccessfully() throws BusinessRuleException {
        Appointment appointment = new Appointment();
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        OffsetDateTime newDate = OffsetDateTime.now().plusDays(2);

        appointment.reschedule(newDate);

        assertEquals(newDate, appointment.getDate());
    }

    @Test
    void shouldThrowExceptionWhenDateIsInPast() {
        Appointment appointment = new Appointment();
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        OffsetDateTime pastDate = OffsetDateTime.now().minusDays(1);

        assertThrows(BusinessRuleException.class,
                () -> appointment.reschedule(pastDate));
    }

    @Test
    void shouldThrowExceptionWhenAppointmentIsCompleted() {
        Appointment appointment = new Appointment();
        appointment.setStatus(AppointmentStatus.COMPLETED);

        OffsetDateTime newDate = OffsetDateTime.now().plusDays(1);

        assertThrows(BusinessRuleException.class,
                () -> appointment.reschedule(newDate));
    }

    @Test
    void shouldThrowExceptionWhenAppointmentIsCanceled() {
        Appointment appointment = new Appointment();
        appointment.setStatus(AppointmentStatus.CANCELED);

        OffsetDateTime newDate = OffsetDateTime.now().plusDays(1);

        assertThrows(BusinessRuleException.class,
                () -> appointment.reschedule(newDate));
    }
}
