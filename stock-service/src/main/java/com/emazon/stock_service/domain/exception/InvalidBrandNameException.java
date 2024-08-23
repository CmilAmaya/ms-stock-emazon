package com.emazon.stock_service.domain.exception;

public class InvalidBrandNameException extends RuntimeException {
    public InvalidBrandNameException(String message) {
        super(message);
    }
}
