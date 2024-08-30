package com.emazon.stock_service.domain.exception;

public class InvalidItemNameException extends RuntimeException {
    public InvalidItemNameException(String message) {
        super(message);
    }
}
