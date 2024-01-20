package com.postech.parquimetro.service.impl;

import com.postech.parquimetro.domain.enums.SessionType;
import com.postech.parquimetro.domain.session.ParkingSessionDTO;
import com.postech.parquimetro.service.TimeCalculatorService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class TimeCalculatorServiceImpl implements TimeCalculatorService {

    @Override
    public long get15MinBeforeExpiration(ParkingSessionDTO sessionDTO) {

        LocalDateTime endSessionMinus15 = sessionDTO.endSession().minusMinutes(15);
        LocalDateTime now = LocalDateTime.now(); //TODO trocar para "America/Sao_Paulo" no Dockerfile

        if (endSessionMinus15.isBefore(now)) {
            //TODO return an exception porque a hora do envio nao pode ser anterior a hora atual
        }

        // Calculando a diferença em milissegundos
        long delayInMillis = Duration.between(now, endSessionMinus15).toMillis();
        System.out.println("Now ------> " + now + " Envio do email ------> " + endSessionMinus15 + " Tempo em milisegundos ------> " + delayInMillis);

        return delayInMillis;
    }

    @Override
    public long get45MinInMilliseconds(ParkingSessionDTO sessionDTO) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime hourMinus15 = now.plusMinutes(45);

        return Duration.between(now, hourMinus15).toMillis();
    }

    @Override
    public long sendMailSessionExtended(String sessionEnd, SessionType sessionType) {
        return 0;
    }
}
