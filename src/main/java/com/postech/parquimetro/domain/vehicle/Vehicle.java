package com.postech.parquimetro.domain.vehicle;

import com.postech.parquimetro.domain.customer.Customer;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "LicensePlate")
public class Vehicle {

//é a key primary
    private String licenseplate;

    private String name;
//@Field, que permite especificar como uma propriedade se relaciona com um campo no documento
    private Customer customer;

    public Vehicle(DataNewVehicle data) {
        this.name = data.name();
        this.licenseplate = data.licenseplate();
    }
}
