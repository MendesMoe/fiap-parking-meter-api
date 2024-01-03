package com.postech.parquimetro.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SessionParkingListenner {

    @RabbitListener(queues = "estacionamento.criado")
    public void receiveMessage(Message message) {
        System.out.println("Recebi a mensagem" + message.toString());
    }
}
