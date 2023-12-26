package com.postech.parquimetro.service;

import com.postech.parquimetro.domain.customer.Customer;

import java.util.List;

public interface CustomerService {

    public List<Customer> getAll();

    public Customer findByLogin(String login);

    public Customer getOneById(String id);

    public Customer create(Customer data);

}
