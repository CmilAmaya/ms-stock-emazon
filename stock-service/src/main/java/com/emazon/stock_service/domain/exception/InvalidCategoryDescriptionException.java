package com.emazon.stock_service.domain.exception;

public class InvalidCategoryDescriptionException extends RuntimeException {
    public InvalidCategoryDescriptionException(String message) {
        super(message);
    }
}
