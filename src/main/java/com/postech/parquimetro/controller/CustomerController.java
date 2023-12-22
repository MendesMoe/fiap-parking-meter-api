package com.postech.parquimetro.controller;

import com.postech.parquimetro.domain.customer.Customer;
import com.postech.parquimetro.domain.customer.CustomerRepository;
import com.postech.parquimetro.domain.customer.DataNewCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerRepository repository;

    private final PasswordEncoder passwordEncoder;

    public CustomerController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity newCustomer(@RequestBody DataNewCustomer data,UriComponentsBuilder uriBuilder){
        var customer = new Customer(data);
        var encryptedPassword = passwordEncoder.encode(data.password());
        customer.setPassword(encryptedPassword);
        repository.save(customer);

        var uri = uriBuilder.path("customer/{customerID}").buildAndExpand(customer.getCustomerID()).toUri();
        return ResponseEntity.ok("ok");
    }
}
