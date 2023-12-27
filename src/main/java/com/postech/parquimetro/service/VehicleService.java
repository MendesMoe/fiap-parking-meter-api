package com.postech.parquimetro.service;

import com.postech.parquimetro.domain.vehicle.Vehicle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VehicleService {

    public List<Vehicle> getAll();

    public Vehicle create(Vehicle vehicle);

    public Vehicle getById(String licenseplate);

}
