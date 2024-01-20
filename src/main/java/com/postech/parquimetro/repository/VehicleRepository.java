package com.postech.parquimetro.repository;

import com.postech.parquimetro.domain.vehicle.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, String> { }
