package com.postech.parquimetro.controller;

import com.postech.parquimetro.domain.customer.Customer;
import com.postech.parquimetro.domain.vehicle.NewVehicleDTO;
import com.postech.parquimetro.domain.vehicle.Vehicle;
import com.postech.parquimetro.repository.CustomerRepository;
import com.postech.parquimetro.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    @Operation(summary = "Create a new vehicle", responses = {
            @ApiResponse(description = "The vehicle has been created", responseCode = "201")
    })
    public ResponseEntity create(@RequestBody NewVehicleDTO vehicleDTO){

        System.out.println("Veiculo DTO -----> " + vehicleDTO );

        //Primeiro verifica se esse customer existe na db
        Customer customer = this.customerRepository.findById(vehicleDTO.customerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with the ID: " + vehicleDTO.customerId()));

        //Se nao existir, retorna uma exception
        //Se existir cria o veiculo en db
        Vehicle created = this.vehicleService.create(new Vehicle(vehicleDTO));

        // Depois de criar o vehicle precisa atribui-lo ao customer
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(created);
        customer.setVehicles(vehicleList);
        this.customerRepository.save(customer);

        return ResponseEntity.ok("vehicle created");
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
    public ResponseEntity deleteById(@PathVariable String license){

        System.out.println("license-plate ------> " + license);
        this.vehicleService.deleteById(license);
        return ResponseEntity.ok("delete ok");
    }
}
