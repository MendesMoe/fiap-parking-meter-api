package com.postech.parquimetro.domain.vehicle;

import jakarta.validation.constraints.NotBlank;

public record NewVehicleDTO(@NotBlank
                            String name,
                            @NotBlank
                            String licenseplate,
                            @NotBlank
                            String customerId) {
}
