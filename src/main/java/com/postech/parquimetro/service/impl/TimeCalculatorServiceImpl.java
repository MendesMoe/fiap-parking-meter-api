package com.postech.parquimetro.service.impl;

import com.postech.parquimetro.domain.enums.SessionType;
import com.postech.parquimetro.domain.session.ParkingSessionDTO;
import com.postech.parquimetro.service.TimeCalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Service
public class TimeCalculatorServiceImpl implements TimeCalculatorService {

    final BigDecimal PRICE_PER_HOUR = new BigDecimal("10");

    @Override
    public long get15MinBeforeExpiration(ParkingSessionDTO sessionDTO) {

        LocalDateTime endSessionMinus15 = sessionDTO.endSession().minusMinutes(15);
        LocalDateTime now = LocalDateTime.now();

        log.info("NOW ----> " + now + " endSessionMinus15 ----> " + endSessionMinus15);

        // Calculando a diferença em milissegundos
        long delayInMillis = Duration.between(now, endSessionMinus15).toMillis();
        log.info("Now ---> " + now + " Envio do email ---> " + endSessionMinus15 + " Tempo em milisegundos ---> " + delayInMillis);

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

    public BigDecimal calculatePriceForDuration (Duration duration){
        log.debug("calculate price");
        BigDecimal totalPrice = PRICE_PER_HOUR.multiply(new BigDecimal(duration.toHours()));
        if(duration.toMinutesPart() > 0) {
            totalPrice = totalPrice.add(PRICE_PER_HOUR);
        }
        return totalPrice;
      }
}
