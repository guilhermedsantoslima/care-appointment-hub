package br.com.notification_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "appointment.exchange";
    public static final String QUEUE = "appointment.queue";
    public static final String UPDATED_QUEUE = "appointment.updated.queue";
    public static final String ROUTING_KEY = "appointment.created";
    public static final String UPDATED_ROUTING_KEY = "appointment.updated";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }

    @Bean
    public Queue updatedQueue() {
        return new Queue(UPDATED_QUEUE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {

        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }

    @Bean
    public Binding updatedBinding(TopicExchange exchange) {

        return BindingBuilder
                .bind(updatedQueue())
                .to(exchange)
                .with(UPDATED_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
