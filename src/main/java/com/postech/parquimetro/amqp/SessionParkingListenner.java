package com.postech.parquimetro.amqp;

import com.postech.parquimetro.domain.session.ParkingSessionDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SessionParkingListenner {

    @RabbitListener(queues = "estacionamento.criado")
    public void receiveMessage(ParkingSessionDTO sessionDTO) {
        System.out.println("Recebi a mensagem" + sessionDTO);
    }
}
