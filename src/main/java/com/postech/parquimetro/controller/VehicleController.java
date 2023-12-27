package com.postech.parquimetro.controller;

import com.postech.parquimetro.domain.vehicle.Vehicle;
import com.postech.parquimetro.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping
    public ResponseEntity createVehicle(@RequestBody Vehicle vehicle){
        this.vehicleService.create(vehicle);
        return ResponseEntity.ok("ok");
    }

    @GetMapping
    public List<Vehicle> getAllVehicles(){

        return this.vehicleService.getAll();
    }

    @GetMapping("/{licenseplate}")
    public Vehicle getById(@PathVariable String licenseplate){
        return this.vehicleService.getById(licenseplate);
    }
}
