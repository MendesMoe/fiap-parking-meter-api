package com.postech.parquimetro.service.impl;

import com.postech.parquimetro.domain.enums.SessionType;
import com.postech.parquimetro.domain.session.ParkingSessionDTO;
import com.postech.parquimetro.service.EmailService;
import com.postech.parquimetro.service.SessionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private final JavaMailSender emailSender;

    @Autowired
    private SessionService sessionService;

    public EmailServiceImpl(final JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Transactional
    @Override
    public void sendEmail(SimpleMailMessage message) {

        try{
            emailSender.send(message);
        } catch (MailException e){
            System.out.println("erro ao enviar o email no service sendEmail = " + e.getMessage());
        }
    }

    // Methode qui va écouter la queue MyQueue et a chaque fois qu'un message sera arrivé le DTO sera analysé et un mail sera envoyé au client
    @RabbitListener(queues = "myQueue")
    public void sendMail15MinutesBeforeExpiration(ParkingSessionDTO sessionDTO) {

        System.out.println("mensagem recebida e consumida no listenner = " + sessionDTO);

        //Check si la session est toujours active et cas de string pour int
        //ParkingSession parkingSession = this.sessionService.getById(sessionDTO.id()); //TODO aqui estou com problema para recuperar a session pelo ID, quer Long e nao String ?
        //
        //        if (parkingSession.getStatus() == 0) {
        //            //TODO nao envia o email porque a sessao ja foi encerrada
        //        } else {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("parquimetro.bjmmt@gmail.com");
            message.setTo(sessionDTO.customerMail());

            if (sessionDTO.sessionType() == SessionType.FIXED_TIME) {
                message.setSubject("Sua sessao vai expirar em 15 minutos");
                message.setText("Bom dia! O seu tempo de estacionamento vai acabar em 15 minutos. Se você nao retirar o seu carro e cancelar a sessao, ela sera renovada por mais 1 hora");
            } else {
                message.setSubject("Sua sessao sera renovada em 15 minutos");
                message.setText("Bom dia! O seu tempo de estacionamento completou 45 minutos. Se você nao retirar o seu carro e cancelar a sessao, ela sera renovada por mais 1 hora");
            }

            // Se ela nao esta ativa, entao faz o envio do email e depois tem que update o endSession para 1 hora a mais e reprogramar o envio do email
            this.sendEmail(message);
            System.out.println("mail criado e enviado");
        //}
    }
}
