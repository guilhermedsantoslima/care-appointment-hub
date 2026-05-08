package br.com.care_appointment_hub.infra.messages.consumer;

import br.com.care_appointment_hub.infra.messages.event.AppointmentCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    @RabbitListener(queues = "appointment.queue")
    public void receive(AppointmentCreatedEvent event){
        System.out.println("=================================");
        System.out.println("📩 NOVA CONSULTA RECEBIDA");
        System.out.println("Appointment ID: " + event.appointmentId());
        System.out.println("Patient ID: " + event.patientId());
        System.out.println("Date: " + event.date());
        System.out.println("=================================");
    }
}
