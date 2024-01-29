package com.postech.parquimetro.controller;

import com.postech.parquimetro.domain.customer.Customer;
import com.postech.parquimetro.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
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
    public List<Customer> getAllCustomers(@RequestParam(name = "name", required = false) String name) throws ValidationException {
        return customerService.getAll(name);
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
    public ResponseEntity newCustomer(@RequestBody @Valid Customer customer) {
        try {
            this.customerService.create(customer);
            return ResponseEntity.ok("customer created");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    @Operation(summary = "Update all informations of a customer", responses = {
            @ApiResponse(description = "The customer was updated", responseCode = "200")
    })
    public ResponseEntity <Customer> update(@RequestBody @Valid Customer customer){
		try {
			customer = this.customerService.update(customer);
			return ResponseEntity.ok(customer);
		} catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException
                    ("Erro ao atualizar cliente: " + e.getMessage());
        }
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
