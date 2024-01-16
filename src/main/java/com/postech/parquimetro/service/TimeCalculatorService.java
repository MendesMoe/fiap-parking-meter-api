package com.postech.parquimetro.service;

import com.postech.parquimetro.domain.enums.SessionType;
import com.postech.parquimetro.domain.session.ParkingSessionDTO;
import org.springframework.stereotype.Service;

@Service
public interface TimeCalculatorService {

    long get15MinBeforeExpiration(ParkingSessionDTO sessionDTO);

    long get45MinInMilliseconds(ParkingSessionDTO sessionDTO);

    long sendMailSessionExtended(String sessionEnd, SessionType sessionType);

}
