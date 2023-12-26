package com.postech.parquimetro.service.impl;

import com.postech.parquimetro.domain.vehicle.DataNewVehicle;
import com.postech.parquimetro.domain.vehicle.Vehicle;
import com.postech.parquimetro.repository.VehicleRepository;
import com.postech.parquimetro.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository repository;

    @Override
    public List<Vehicle> getAll() {
        return null;
    }

    @Override
    public Vehicle getByCustomer(String id) {
        return null;
    }

    @Override
    public Vehicle create(DataNewVehicle data) {
        return null;
    }
}
