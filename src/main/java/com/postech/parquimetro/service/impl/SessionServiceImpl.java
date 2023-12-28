package com.postech.parquimetro.service.impl;

import com.postech.parquimetro.domain.customer.Customer;
import com.postech.parquimetro.domain.enums.PaymentMethod;
import com.postech.parquimetro.domain.enums.SessionType;
import com.postech.parquimetro.domain.session.ParkingSession;
import com.postech.parquimetro.domain.vehicle.Vehicle;
import com.postech.parquimetro.repository.CustomerRepository;
import com.postech.parquimetro.repository.SessionRepository;
import com.postech.parquimetro.service.SessionService;
import com.postech.parquimetro.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VehicleService vehicleService;

    @Override
    public List<ParkingSession> getAll() {
        return this.sessionRepository.findAll();
    }

    @Override
    public ParkingSession create(ParkingSession parkingSession) {

        //check s'il a un customer avec cet id
        Customer customer = this.customerRepository.findById(parkingSession.getCustomer().getCustomerID())
                        .orElseThrow(() -> new IllegalArgumentException("Customer not found with the ID: " + parkingSession.getCustomer().getCustomerID()));
        parkingSession.setCustomer(customer);

        //Se a forma de pagamento do customer é PIX, ele nao pode escolher o FREE_TIME, entao nos ja fazemos set pra ele.
        if (customer.getPaymentPreference() != null && customer.getPaymentPreference() == PaymentMethod.PIX) {
            parkingSession.setSessionType(SessionType.FIXED_TIME);
        }

        //check s'il existe un vehicule avec la plaque d'immatriculation informée
        Vehicle vehicle = this.vehicleService.getById(parkingSession.getVehicle().getLicenseplate()); //TODO check if the vehicle belongs to the customer ?
        parkingSession.setVehicle(vehicle);

        return this.sessionRepository.save(parkingSession);
    }

    @Override
    public ParkingSession getById(Long id) {
        return this.sessionRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("The Session has not found with ID: " + id));
    }

    @Override // funciona com um id existente mas se for um customerid inexistente devolve um 403. Procurar docs sobre excessoes
    public List<ParkingSession> getByCustomer(String customerID) {
        Customer customer = this.customerRepository.findById(customerID)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + customerID));

        return this.sessionRepository.findByCustomer(customer);
    }

    @Override
    public ParkingSession toEndTheSession(String id) {
        return null;
    }
}