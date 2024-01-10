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

import java.util.HashMap;
import java.util.Map;


@Configuration
public class NotificationAMQPConfiguration {

    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory con){
        return new RabbitAdmin(con);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initAdmin(RabbitAdmin rabbitAdmin){
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public Queue myQueue() {
        return new Queue("myQueue", true);
    }

    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange("myDelayedExchange", "x-delayed-message", true, false, args);
    }

    @Bean
    public Binding binding(Queue myQueue, CustomExchange delayedExchange) {
        return BindingBuilder.bind(myQueue).to(delayedExchange).with("myRoutingKey").noargs();
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
