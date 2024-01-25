package com.postech.parquimetro.service;

import com.postech.parquimetro.domain.customer.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    public List<Customer> getAll(String name);

    public Customer findByLogin(String login);

    public Customer getOneById(String id);

    public Customer create(Customer data);

    public Customer update(Customer updateCustomer);

    public void deleteById(String id);

}
