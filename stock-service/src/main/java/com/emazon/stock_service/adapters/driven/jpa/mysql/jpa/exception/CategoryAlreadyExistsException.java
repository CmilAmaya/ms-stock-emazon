package com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.exception;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String message) {
        super(message);
    }
}
