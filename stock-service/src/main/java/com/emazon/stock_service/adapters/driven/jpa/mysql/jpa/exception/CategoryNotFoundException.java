package com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
