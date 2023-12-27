package com.postech.parquimetro.service.impl;

import com.postech.parquimetro.domain.customer.Customer;
import com.postech.parquimetro.domain.vehicle.Vehicle;
import com.postech.parquimetro.repository.CustomerRepository;
import com.postech.parquimetro.repository.VehicleRepository;
import com.postech.parquimetro.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

        // Get the vehicles in a reference before saving in db
        if(customer.getVehicles() != null && !customer.getVehicles().isEmpty()) {
            List<Vehicle> updatedVehicles = customer.getVehicles().stream()
                    .map(vehicle -> this.vehicleRepository.findById(vehicle.getLicenseplate())
                            .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with license plate: " + vehicle.getLicenseplate())))
                    .collect(Collectors.toList());

            customer.setVehicles(updatedVehicles);
        } else {
            customer.setVehicles(new ArrayList<>()); // Set to empty list instead of null
        }

        return this.customerRepository.save(customer);
    }

    @Override
    public Customer update(Customer updateCustomer) {
        return this.customerRepository.save(updateCustomer);
    }
    @Override
    public void deleteById(String id){
        this.customerRepository.deleteById(id);
    }
}
