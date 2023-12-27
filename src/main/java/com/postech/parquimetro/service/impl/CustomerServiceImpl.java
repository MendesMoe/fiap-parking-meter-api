package com.postech.parquimetro.service.impl;

import com.postech.parquimetro.domain.customer.Customer;
import com.postech.parquimetro.domain.vehicle.Vehicle;
import com.postech.parquimetro.repository.CustomerRepository;
import com.postech.parquimetro.repository.VehicleRepository;
import com.postech.parquimetro.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public List<Customer> getAll() {
        return this.customerRepository.findAll();
    }

    @Override
    public Customer findByLogin(String login) {
        return (Customer) this.customerRepository.findByLogin(login);
    }

    @Override
    public Customer getOneById(String id) {
        return this.customerRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("The Customer has not found")); // Se nao achar, joga uma excessao
    }

    @Override
    public Customer create(Customer customer) {
        //encrypt the password before save in db
        var encryptedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encryptedPassword);

        //get the vehicle in a reference before save in db
        if(customer.getVehicle() != null) {

            Vehicle vehicle = this.vehicleRepository
                    .findById(customer.getVehicle().getLicenseplate())
                    .orElseThrow(()-> new IllegalArgumentException("Vehicle not found"));

            customer.setVehicle(vehicle);
        } else {
            customer.setVehicle(null);
        }

        return this.customerRepository.save(customer);
    }
}
