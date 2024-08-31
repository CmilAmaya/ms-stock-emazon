package com.emazon.stock_service.domain.exception;

public class InvalidCategoryNumberException extends RuntimeException {
    public InvalidCategoryNumberException(String message) {
        super(message);
    }
}
