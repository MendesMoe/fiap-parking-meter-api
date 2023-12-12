package com.postech.parquimetro.infra.exception;

public class ValidationDataException extends RuntimeException {
    public ValidationDataException(String message) {
        super(message);
    }
}
