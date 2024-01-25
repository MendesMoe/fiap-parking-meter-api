package com.postech.parquimetro.controller;

import com.postech.parquimetro.domain.vehicle.Vehicle;
import com.postech.parquimetro.domain.vehicle.VehicleDTO;
import com.postech.parquimetro.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vehicle")
@Slf4j
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping
    @Operation(summary = "Create a new vehicle", responses = {
            @ApiResponse(description = "The vehicle has been created", responseCode = "201")
    })
    public ResponseEntity<Vehicle> create(@RequestBody VehicleDTO vehicleDTO){

        log.info("Veiculo DTO -----> {}" + vehicleDTO );

       Vehicle vehicle = vehicleService.create(new Vehicle(vehicleDTO));

        return ResponseEntity.ok(vehicle);
    }

    @GetMapping
    @Operation(summary = "Get all vehicles", responses = {
            @ApiResponse(description = "List of all vehicles", responseCode = "200")
    })
    public List<Vehicle> getAllVehicles(){

        return this.vehicleService.getAll();
    }

    @GetMapping("/{licenseplate}")
    @Operation(summary = "Get one vehicle with his license-plate", responses = {
            @ApiResponse(description = "The vehicle with this license-plate", responseCode = "200")
    })
    public Vehicle getById(@PathVariable String licenseplate){
        return this.vehicleService.getById(licenseplate);
    }


    @DeleteMapping("/{license}")
    @Operation(summary = "Delete one vehicle by license-plate", responses = {
            @ApiResponse(description = "The vehicle was deleted", responseCode = "200")
    })
    public ResponseEntity deleteByLicense(@PathVariable String license){

        System.out.println("license-plate ------> " + license);
        this.vehicleService.deleteByLicense(license);
        return ResponseEntity.ok("delete ok");
    }
}
