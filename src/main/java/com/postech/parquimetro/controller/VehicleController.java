package com.postech.parquimetro.controller;

import com.postech.parquimetro.domain.customer.Customer;
import com.postech.parquimetro.domain.customer.CustomerRepository;
import com.postech.parquimetro.domain.vehicle.DataNewVehicle;
import com.postech.parquimetro.domain.vehicle.Vehicle;
import com.postech.parquimetro.domain.vehicle.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("vehicle")
public class VehicleController {

    @Autowired
    private VehicleRepository repository;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    @Transactional
    public ResponseEntity createVehicle(@RequestBody @Valid DataNewVehicle data){

        Customer customer = customerRepository.findById(data.CustomerID())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        var vehicle = new Vehicle(data);
        vehicle.setCustomer(customer);
        repository.save(vehicle);
        return ResponseEntity.ok("ok");
    }
}
