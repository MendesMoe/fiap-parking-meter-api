package com.postech.parquimetro.domain.vehicle;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @NotNull
    private String licensePlate;
    @NotNull
    private String name;

    @NotNull
    private String customerId;

    public Vehicle(VehicleDTO vehicleDTO) {
        this.name = vehicleDTO.name();
        this.licensePlate = vehicleDTO.licensePlate();
        this.customerId = vehicleDTO.customerId();
    }
}
