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
    private String licenseplate;

    private String name;

    @NotNull
    private String customerId;

    public Vehicle(NewVehicleDTO vehicleDTO) {
        this.name = vehicleDTO.name();
        this.licenseplate = vehicleDTO.licenseplate();
        this.customerId = vehicleDTO.customerId();
    }
}
