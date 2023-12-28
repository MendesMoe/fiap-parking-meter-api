package com.postech.parquimetro.service;

import com.postech.parquimetro.domain.session.ParkingSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SessionService {

    public List<ParkingSession> getAll();

    public ParkingSession create(ParkingSession parkingSession);

    public ParkingSession getById(Long id);

    public List<ParkingSession> getByCustomer(String customerID);

    public ParkingSession toEndTheSession(String id);
}
