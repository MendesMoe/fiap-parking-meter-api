package com.postech.parquimetro.service.impl;

import com.postech.parquimetro.domain.email.Email;
import com.postech.parquimetro.domain.enums.SessionType;
import com.postech.parquimetro.domain.session.ParkingSession;
import com.postech.parquimetro.domain.session.ParkingSessionDTO;
import com.postech.parquimetro.repository.EmailRepository;
import com.postech.parquimetro.service.EmailService;
import com.postech.parquimetro.service.SessionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

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

    // Methode que vai escutar a queue MyQueue e fazer o envio da notificacao por email
    @RabbitListener(queues = "myQueue")
    public void sendMail15MinutesBeforeExpiration(ParkingSessionDTO sessionDTO) {

        System.out.println("mensagem recebida e consumida no listenner = " + sessionDTO);

        //check se a session ainda nao foi desativada pelo utilisador. Se ativa, ela tera o status = 1
        if (sessionDTO.status() == 0) {
            //TODO nao envia o email porque a sessao ja foi encerrada
        } else {
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
            this.sendEmail(message);
            System.out.println("Email before15minutes criado e enviado");
        }
    }

    public void sendMailWithInvoice(ParkingSessionDTO sessionDTO){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("parquimetro.bjmmt@gmail.com");
        message.setTo(sessionDTO.customerMail());

        message.setSubject("Sua sessão no Parquímetro foi encerrada");

        String text = String.format(
                "Olá!\n\n" + //TODO add o nome do cliente para que o recibo seja mais personalizado
                        "Sua sessão no parquímetro foi encerrada com sucesso.\n" +
                        "Detalhes da sessão:\n" +
                        "- Tempo total: %s\n" +
                        "- Método de pagamento: %s\n" +
                        "- Total da fatura: %s\n\n" +
                        "Agradecemos por utilizar nossos serviços e esperamos vê-lo novamente em breve.\n\n" +
                        "Atenciosamente,\n" +
                        "Equipe Parquímetro",
                //sessionDTO.duration(), // TODO esperar merge do metodo endSession
                sessionDTO.paymentMethod(),
                sessionDTO.price()
        );

        message.setText(text);

        this.sendEmail(message);
        System.out.println("Email com recibo criado e enviado");
    }
}
