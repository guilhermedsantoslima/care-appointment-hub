package br.com.care_appointment_hub.infra.messages.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange("appointment.exchange");
    }

    @Bean
    public Queue queue(){
        return new Queue("appointment.queue");
    }

    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with("appointment.*");
    }

    @Bean
    public JacksonJsonMessageConverter messageConverter(){
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory,
                                         JacksonJsonMessageConverter messageConverter){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);

        return template;
    }
}
