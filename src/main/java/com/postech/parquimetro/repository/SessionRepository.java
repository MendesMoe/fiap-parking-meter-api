package com.postech.parquimetro.repository;

import com.postech.parquimetro.domain.customer.Customer;
import com.postech.parquimetro.domain.enums.SessionType;
import com.postech.parquimetro.domain.session.ParkingSession;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends MongoRepository<ParkingSession, Long> {
    public List<ParkingSession> findByCustomer(Customer customer);

    public void deleteById(String id);

    public ParkingSession getById(String id);

    public List<ParkingSession> findAllByEndSessionAfterAndStatusAndSessionType(LocalDateTime endSession, Integer status, SessionType type);
}
