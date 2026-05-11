package br.com.care_appointment_hub.infra.messages.producer;

import br.com.care_appointment_hub.application.port.output.AppointmentEventPublisherPort;
import br.com.care_appointment_hub.domain.model.Appointment;
import br.com.care_appointment_hub.infra.messages.event.AppointmentCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AppointmentEventPublisher implements AppointmentEventPublisherPort {
    private final RabbitTemplate rabbitTemplate;

    public AppointmentEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    @Override
    public void publishCreatedEvent(Appointment appointment) {
        rabbitTemplate.convertAndSend(
                "appointment.exchange",
                "appointment.created",
                new AppointmentCreatedEvent(
                        appointment.getId(),
                        appointment.getPatientId(),
                        appointment.getDate()
                )
        );
    }

    @Override
    public void publishUpdatedEvent(Appointment appointment) {
        AppointmentCreatedEvent event = new AppointmentCreatedEvent(
                appointment.getId(),
                appointment.getPatientId(),
                appointment.getDate()
        );

        rabbitTemplate.convertAndSend(
                "appointment.exchange",
                "appointment.updated",
                event);
    }
}
