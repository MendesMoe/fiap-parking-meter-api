package com.postech.parquimetro.amqp;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class NotificationAMQPConfiguration {

    @Bean
    public Queue sessionExpirate(){
        return QueueBuilder.durable("session.expiration").build();
    }
    @Bean
    DirectExchange sessionExpireExchange() {
        return new DirectExchange("sessionExpireExchange");
    }
    @Bean
    Binding DLQbinding(Queue sessionExpirate, DirectExchange sessionExpireExchange) {
        return BindingBuilder.bind(sessionExpirate).to(sessionExpireExchange).with("deadLetter");
    }

    @Bean
    Queue myQueue() {
        return QueueBuilder.durable("myQueue")
                .withArgument("x-dead-letter-exchange", "sessionExpireExchange")
                .withArgument("x-dead-letter-routing-key", "deadLetter")
                .build();
    }
    @Bean
    DirectExchange myExchange() {
        return new DirectExchange("myExchange");
    }

    @Bean
    Binding binding(Queue myQueue, DirectExchange myExchange) {
        return BindingBuilder.bind(myQueue).to(myExchange).with("myRoutingKey");
    }

    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory con){
        return new RabbitAdmin(con);
    }
    @Bean
    public ApplicationListener<ApplicationReadyEvent> initAdmin(RabbitAdmin rabbitAdmin){
        return event -> rabbitAdmin.initialize();
    }

    //Os dois metodos a seguir possibilitam o envio de uma entidade em formato json nas mensagens. Par default eles so aceitavam bytes. Com as config seguintes, fica mais facil.

    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());  // Registra o JavaTimeModule
        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory con,
    Jackson2JsonMessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(con);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
