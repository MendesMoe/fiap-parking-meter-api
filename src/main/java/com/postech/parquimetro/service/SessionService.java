package com.postech.parquimetro.service;

import com.postech.parquimetro.domain.session.ParkingSession;
import com.postech.parquimetro.domain.session.ParkingSessionDTO;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;

@Service
public interface SessionService {

    public List<ParkingSession> getAll();

    public ParkingSessionDTO create(ParkingSession parkingSession) throws ValidationException;

    public ParkingSession getById(String id);

    public List<ParkingSession> getByCustomer(String customerID);

    public ParkingSessionDTO endTheSession(String sessionID);

    public ParkingSession update(ParkingSession updateSession);

    public void deleteById(String id);
}
