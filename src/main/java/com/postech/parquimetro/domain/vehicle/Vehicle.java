package com.postech.parquimetro.domain.vehicle;

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
}
