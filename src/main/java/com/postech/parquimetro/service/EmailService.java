package com.postech.parquimetro.service;

import com.postech.parquimetro.domain.email.Email;
import com.postech.parquimetro.domain.session.ParkingSessionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    public void sendEmail(SimpleMailMessage message);

    public void sendMail15MinutesBeforeExpiration(ParkingSessionDTO sessionDTO);

}
