package com.postech.parquimetro.service.impl;

import com.postech.parquimetro.domain.customer.Customer;
import com.postech.parquimetro.domain.vehicle.Vehicle;
import com.postech.parquimetro.repository.CustomerRepository;
import com.postech.parquimetro.repository.VehicleRepository;
import com.postech.parquimetro.service.VehicleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Vehicle> getAll() {

        return this.vehicleRepository.findAll();
    }

    @Override // incluir um pathvariable com o id do customer
    public Vehicle create(@Valid Vehicle vehicle) {
        //Primeiro verifica se esse customer existe na db
        Customer customer = this.customerRepository.findById(vehicle.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with the ID: " + vehicle.getCustomerId()));

        //Se nao existir, retorna uma exception
        //Se existir cria o veiculo en db
        Vehicle created = vehicleRepository.save(vehicle);

        // Depois de criar o vehicle precisa atribui-lo ao customer
        List<Vehicle> vehicleList = customer.getVehicles();
        vehicleList.add(created);
        customer.setVehicles(vehicleList);
        this.customerRepository.save(customer);

        // junto com as informacoes do novo carro, eu preciso do id cliente para fazer a relacao.
        return created;
    }

    @Override
    public Vehicle getById(String licenseplate) {
        return this.vehicleRepository.findById(licenseplate)
                .orElseThrow(()-> new IllegalArgumentException("The Vehicle has not found"));
    }

    @Override
    public void deleteByLicense(String license) {
        try {
            vehicleRepository.deleteById(license);
        } catch (Exception e){
            log.error("error deleting vehicle with license: [{}]", license);
        }
    }

}
