package com.postech.parquimetro.repository;

import com.postech.parquimetro.domain.vehicle.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleRepository extends MongoRepository<Vehicle, String> {
}
