package com.postech.parquimetro.amqp;

import com.postech.parquimetro.domain.session.ParkingSessionDTO;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SessionParkingListenner {

    //@RabbitListener(queues = "myQueue")
    //    public void receiveMessage(ParkingSessionDTO sessionDTO) {
    //        System.out.println("Recebi a mensagem : " + sessionDTO);
    //
    //        // Check se a sessao ainda esta ativa. status = 1. Se for status = 0 nao precisa mais enviar
    //        //Chamar o servico de notificacao para avisar o customer
    //    }
}
