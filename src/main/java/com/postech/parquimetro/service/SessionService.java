package com.postech.parquimetro.service;

import com.postech.parquimetro.domain.customer.Customer;
import com.postech.parquimetro.domain.session.ParkingSession;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;

@Service
public interface SessionService {

    public List<ParkingSession> getAll();

    public ParkingSession create(ParkingSession parkingSession) throws ValidationException;

    public ParkingSession getById(Long id);

    public List<ParkingSession> getByCustomer(String customerID);

    public ParkingSession toEndTheSession(String id);

    public ParkingSession update(ParkingSession updateSession);

    public void deleteById(String id);
}
