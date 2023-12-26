package com.postech.parquimetro.service;

import com.postech.parquimetro.domain.vehicle.DataNewVehicle;
import com.postech.parquimetro.domain.vehicle.Vehicle;

import java.util.List;

public interface VehicleService {
    public List<Vehicle> getAll();

    public Vehicle getByCustomer(String id);

    public Vehicle create(DataNewVehicle data);

}
