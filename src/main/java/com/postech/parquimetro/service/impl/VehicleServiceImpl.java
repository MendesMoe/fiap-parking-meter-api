package com.postech.parquimetro.service.impl;

import com.postech.parquimetro.domain.vehicle.Vehicle;
import com.postech.parquimetro.repository.VehicleRepository;
import com.postech.parquimetro.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository repository;

    @Override
    public List<Vehicle> getAll() {

        return this.repository.findAll();
    }

    @Override
    public Vehicle create(Vehicle vehicle) {

        return this.repository.save(vehicle);
    }

    @Override
    public Vehicle getById(String licenseplate) {
        return this.repository.findById(licenseplate)
                .orElseThrow(()-> new IllegalArgumentException("The Vehicle has not found"));
    }

}
