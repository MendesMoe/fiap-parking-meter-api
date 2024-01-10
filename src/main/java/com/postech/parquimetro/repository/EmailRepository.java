package com.postech.parquimetro.repository;

import com.postech.parquimetro.domain.email.Email;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface EmailRepository extends MongoRepository<Email, UUID> {
}
