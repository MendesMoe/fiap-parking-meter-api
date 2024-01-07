package com.postech.parquimetro.amqp;

import com.postech.parquimetro.domain.session.ParkingSessionDTO;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SessionParkingListenner {

    //@RabbitListener(queues = "session.expiration")
    //public void receiveMessage(ParkingSessionDTO sessionDTO) {
       // System.out.println("Recebi a mensagem" + sessionDTO);
    //}
}
