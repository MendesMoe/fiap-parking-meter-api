package com.postech.parquimetro.controller;

import com.postech.parquimetro.domain.customer.Customer;
import com.postech.parquimetro.domain.customer.DataAuth;
import com.postech.parquimetro.infra.security.DataTokenJWT;
import com.postech.parquimetro.infra.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Operation(summary = "Authentication with the login and password by customer", responses = {
            @ApiResponse(description = "Bearer token for requests", responseCode = "200")
    })
    public ResponseEntity login(@RequestBody @Valid DataAuth data) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var authentication = manager.authenticate(authToken);
            var token = tokenService.newToken((Customer) authentication.getPrincipal());

            return ResponseEntity.ok(new DataTokenJWT(token));
        } catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
