package com.postech.parquimetro.domain.vehicle;

import com.postech.parquimetro.domain.customer.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

@Table(name="vehicle")
@Entity(name="Vehicle")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "LicensePlate")
public class Vehicle {

    @Id
    @Column(name = "Licenseplate")
    private String licenseplate;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="customerid")
    private Customer customer;

    public Vehicle(DataNewVehicle data) {
        this.name = data.name();
        this.licenseplate = data.licenseplate();
    }

    // Método para definir customer separadamente
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
