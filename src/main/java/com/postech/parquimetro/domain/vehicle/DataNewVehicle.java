package com.postech.parquimetro.domain.vehicle;

import com.postech.parquimetro.domain.customer.Customer;
import jakarta.validation.constraints.NotNull;

public record DataNewVehicle(
        String Name,
        @NotNull
        String LicensePlate,
        @NotNull
        Long CustomerID
) {}
