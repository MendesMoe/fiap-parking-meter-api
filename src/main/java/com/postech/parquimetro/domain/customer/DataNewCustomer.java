package com.postech.parquimetro.domain.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DataNewCustomer(
        @NotBlank
        String login,
        @NotBlank
        String password,
        String firstname,
        String lastname,
        String address1,
        String address2,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String phone,
        int paymentpreferenceid
) {
}
