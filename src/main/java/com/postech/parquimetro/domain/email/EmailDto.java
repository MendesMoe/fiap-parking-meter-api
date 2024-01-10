package com.postech.parquimetro.domain.email;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;
import lombok.Data;

public record EmailDto (

    @NotBlank
    String ownerRef,
    @NotBlank
    @Email
    String emailFrom,
    @NotBlank
    @Email
    String emailTo,
    @NotBlank
    String subject,
    @NotBlank
    String text

) {
}
