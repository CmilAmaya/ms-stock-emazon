package com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.exception;

public class BrandNotFoundException extends RuntimeException {
    public BrandNotFoundException(String message) {
        super(message);
    }
}
