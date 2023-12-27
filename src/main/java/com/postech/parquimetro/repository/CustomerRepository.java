package com.postech.parquimetro.repository;

import com.postech.parquimetro.domain.customer.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    Customer findByLogin(String login);

    public void deleteById(String id);
}
