package com.postech.parquimetro.service.impl;

import com.postech.parquimetro.domain.customer.Customer;
import com.postech.parquimetro.domain.vehicle.Vehicle;
import com.postech.parquimetro.repository.CustomerRepository;
import com.postech.parquimetro.repository.VehicleRepository;
import com.postech.parquimetro.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public List<Customer> getAll(String name) {
        if (name == null){
            log.debug("list all customers");
            return customerRepository.findAll();
        } else
        {
            log.info("list customers by name");
            return customerRepository.findAllByFirstnameContainingIgnoreCase(name);
        }
    }

    @Override
    public Customer findByLogin(String login) {
        return this.customerRepository.findByLogin(login);
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

        log.info("customer ----------> " + customer);

        // Get the vehicles in a reference before saving in db
        if(customer.getVehicles() != null) {

            System.out.println("customer.getVehicles() ----------> " + customer.getVehicles());

            List<Vehicle> updatedVehicles = customer.getVehicles().stream()
                    .map(vehicle -> this.vehicleRepository.findById(vehicle.getLicensePlate())
                            .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with license plate: " + vehicle.getLicensePlate())))
                    .collect(Collectors.toList());

            //TODO separar a criacao do customer da criacao do vehicle. Primeiro cria o customer e depois o vehicle pode ser criado com o ip do customer para o update

            customer.setVehicles(updatedVehicles);
        } else {
            customer.setVehicles(new ArrayList<>()); // Set to empty list instead of null
        }

        return this.customerRepository.save(customer);
    }

    @Override
    public Customer update(Customer updateCustomer) {
        try {
            return this.customerRepository.save(updateCustomer);
        } catch (Exception e) {
            log.error("error updating customer: [{}]", updateCustomer.getCustomerID());
            throw e;
        }
    }
    @Override
    public void deleteById(String id){
        try {
            this.customerRepository.deleteById(id);
        } catch (Exception e) {
            log.error("error deleting customer: [{}]", id);
            throw e;
        }
    }
}
