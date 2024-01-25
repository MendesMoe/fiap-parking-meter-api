package com.postech.parquimetro.domain.vehicle;

import jakarta.validation.constraints.NotBlank;

public record VehicleDTO(@NotBlank
                            String name,
                         @NotBlank
                            String licensePlate,
                         @NotBlank
                            String customerId) {
}
