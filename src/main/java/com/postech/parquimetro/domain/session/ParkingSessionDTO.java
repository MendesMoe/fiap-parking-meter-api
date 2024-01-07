package com.postech.parquimetro.domain.session;

import com.postech.parquimetro.domain.enums.PaymentMethod;
import com.postech.parquimetro.domain.enums.SessionType;

import java.time.LocalDateTime;

public record ParkingSessionDTO( // Criei o DTO para que seja mais simples passar esse DTO no rabbitTemplate, ao invés de varias infos desnecessarias
        String id,
        String customerId,
        String vehicleId,
        PaymentMethod paymentMethod,
        SessionType sessionType,
        LocalDateTime startSession,
        LocalDateTime endSession,
        Double price,
        Integer status
) {
}
