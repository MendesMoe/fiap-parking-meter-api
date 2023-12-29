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

    @Override // incluir um pathvariable com o id do customer
    public Vehicle create(Vehicle vehicle) {

        return this.repository.save(vehicle);

        // junto com as informacoes do novo carro, eu preciso do id cliente para fazer a relacao.
    }

    @Override
    public Vehicle getById(String licenseplate) {
        return this.repository.findById(licenseplate)
                .orElseThrow(()-> new IllegalArgumentException("The Vehicle has not found"));
    }

}
