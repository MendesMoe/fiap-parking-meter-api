package com.postech.parquimetro.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class NotificationAMQPConfiguration {

    @Bean
    public Queue createQueue(){
        return new Queue("estacionamento.criado", false);
        //return QueueBuilder.nondurable().build("estacionamento.criado");
    }

    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory con){
        return new RabbitAdmin(con);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initAdmin(RabbitAdmin rabbitAdmin){
        return event -> rabbitAdmin.initialize();
    }
}
