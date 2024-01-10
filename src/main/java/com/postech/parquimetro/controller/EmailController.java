package com.postech.parquimetro.controller;

import com.postech.parquimetro.domain.email.Email;
import com.postech.parquimetro.domain.email.EmailDto;
import com.postech.parquimetro.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping("/sending-email")
    @Operation(summary = "Create a email notification", responses = {
            @ApiResponse(description = "Email message detail object", responseCode = "201")
    })
    public ResponseEntity sendingEmail(@RequestBody String toAddressMail) {
        // Email email = new Email();
        //BeanUtils.copyProperties(emailDto, email);

        System.out.println("email deve ser enviado para : " + toAddressMail);
        //emailService.sendEmail(toAddressMail);
        return ResponseEntity.ok("email enviado");
    }
}