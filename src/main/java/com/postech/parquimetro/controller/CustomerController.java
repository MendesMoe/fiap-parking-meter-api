package com.postech.parquimetro.controller;

import com.postech.parquimetro.domain.customer.Customer;
import com.postech.parquimetro.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    private final PasswordEncoder passwordEncoder;

    public CustomerController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerService.getAll();
    }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable String id) {
        return customerService.getOneById(id);
    }

    @PostMapping
    public ResponseEntity newCustomer(@RequestBody Customer customer,UriComponentsBuilder uriBuilder){
        customerService.create(customer);
      //  var uri = uriBuilder.path("customer/{customerID}").buildAndExpand(customer.getCustomerID()).toUri();
        return ResponseEntity.ok("create ok");
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Customer customer){
        customerService.update(customer);
        return ResponseEntity.ok("update ok");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable String id){
        this.customerService.deleteById(id);
        return ResponseEntity.ok("delete ok");
    }
}
