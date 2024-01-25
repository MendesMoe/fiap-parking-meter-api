package com.postech.parquimetro.service.impl;

import com.postech.parquimetro.domain.enums.SessionType;
import com.postech.parquimetro.domain.session.ParkingSession;
import com.postech.parquimetro.domain.session.ParkingSessionDTO;
import com.postech.parquimetro.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private final JavaMailSender emailSender;


    public EmailServiceImpl(final JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Transactional
    @Override
    public void sendEmail(SimpleMailMessage message) {

        try {
            emailSender.send(message);
        } catch (MailException e){
            log.error("error sending an email: {} ", e.getMessage());
        } catch (Exception e){
            log.error("unknown error sending email {}", e.getMessage());
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

    public void sendMailWithInvoice(ParkingSession parkingSession, String durationFormatted){
        log.debug("sending invoice to customer: [{}]", parkingSession.getCustomer().getCustomerID());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("parquimetro.bjmmt@gmail.com");
        message.setTo(parkingSession.getCustomer().getEmail());

        message.setSubject("Sua sessão no Parquímetro foi encerrada");

        String text = String.format(
                "Olá %s!\n\n" +
                        "Sua sessão no parquímetro foi encerrada com sucesso.\n" +
                        "Detalhes da sessão:\n" +
                        "- Tempo total: %s\n" +
                        "- Método de pagamento: %s\n" +
                        "- Total da fatura: R$ %s\n\n" +
                        "Agradecemos por utilizar nossos serviços e esperamos vê-lo novamente em breve.\n\n" +
                        "Atenciosamente,\n" +
                        "Equipe Parquímetro",
                parkingSession.getCustomer().getFirstname(),
                durationFormatted,
                parkingSession.getPaymentMethod().name(),
                parkingSession.getPrice()
        );

        message.setText(text);

        this.sendEmail(message);
        log.info("Email com recibo criado e enviado");
    }
}
