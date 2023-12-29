package com.postech.parquimetro.controller;

import com.postech.parquimetro.domain.customer.Customer;
import com.postech.parquimetro.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Get all customers", responses = {
            @ApiResponse(description = "List of all customers", responseCode = "200")
    })
    public List<Customer> getAllCustomers(){
        return customerService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get one customer by ID", responses = {
            @ApiResponse(description = "The customer with this id", responseCode = "200")
    })
    public Customer getById(@PathVariable String id) {
        return this.customerService.getOneById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new customer", responses = {
            @ApiResponse(description = "The new customer was created", responseCode = "200")
    })
    public ResponseEntity newCustomer(@RequestBody Customer customer,UriComponentsBuilder uriBuilder){
        this.customerService.create(customer);
      //  var uri = uriBuilder.path("customer/{customerID}").buildAndExpand(customer.getCustomerID()).toUri();
        return ResponseEntity.ok("create ok");
    }

    @PutMapping
    @Operation(summary = "Update all informations of a customer", responses = {
            @ApiResponse(description = "The customer was updated", responseCode = "200")
    })
    public ResponseEntity update(@RequestBody Customer customer){
        this.customerService.update(customer);
        return ResponseEntity.ok("update ok");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a customer by ID", responses = {
            @ApiResponse(description = "The customer was deleted", responseCode = "200")
    })
    public ResponseEntity deleteById(@PathVariable String id){
        this.customerService.deleteById(id);
        return ResponseEntity.ok("delete ok");
    }
}
