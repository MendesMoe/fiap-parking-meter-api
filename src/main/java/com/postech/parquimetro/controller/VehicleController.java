package com.postech.parquimetro.controller;

import com.postech.parquimetro.repository.CustomerRepository;
import com.postech.parquimetro.domain.vehicle.DataNewVehicle;
import com.postech.parquimetro.repository.VehicleRepository;
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

        //Optional<Customer> customer = customerRepository.findById(data.customerid());
//
        //var vehicle = new Vehicle(data);
        //vehicle.setCustomer(customer);
        //repository.save(vehicle);
        return ResponseEntity.ok("ok");
    }
}
