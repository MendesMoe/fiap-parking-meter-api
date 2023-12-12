package com.postech.parquimetro.domain.customer;

import com.postech.parquimetro.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    UserDetails findByLogin(String login);
}
