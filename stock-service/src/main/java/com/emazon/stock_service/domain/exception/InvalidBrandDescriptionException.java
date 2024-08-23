package com.emazon.stock_service.domain.exception;

public class InvalidBrandDescriptionException extends RuntimeException {
    public InvalidBrandDescriptionException(String message) {
        super(message);
    }
}
