package com.postech.parquimetro.service.impl;

import com.postech.parquimetro.domain.customer.Customer;
import com.postech.parquimetro.repository.CustomerRepository;
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
        var encryptedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encryptedPassword);
        return this.customerRepository.save(customer);
    }
}
