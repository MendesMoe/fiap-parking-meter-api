package com.postech.parquimetro.repository;

import com.postech.parquimetro.domain.customer.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    Customer findByLogin(String login);
    List<Customer> findAllByFirstnameContainingIgnoreCase(String name);
}
