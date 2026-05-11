package br.com.notification_service.consumer;

import br.com.notification_service.event.AppointmentCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    @RabbitListener(queues = {
            "appointment.queue",
            "appointment.updated.queue"
    })

    public void receive(AppointmentCreatedEvent event){
        System.out.println("Sending notification for appointment: " + event.appointmentId());
        System.out.println("Patient ID: " + event.patientId());
        System.out.println("Date: " + event.date());
    }
}
