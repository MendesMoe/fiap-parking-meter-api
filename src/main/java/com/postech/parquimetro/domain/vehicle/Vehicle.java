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
    @Column(name = "LicensePlate")
    private String licensePlate;

    @Column(name = "Name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="CustomerID")
    private Customer customer;

    public Vehicle(DataNewVehicle data) {
        this.name = data.Name();
        this.licensePlate = data.LicensePlate();
    }

    // Método para definir customer separadamente
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
