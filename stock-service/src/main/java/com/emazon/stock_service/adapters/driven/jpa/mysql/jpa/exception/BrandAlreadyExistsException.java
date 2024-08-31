package com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.exception;

public class BrandAlreadyExistsException extends RuntimeException {
    public BrandAlreadyExistsException(String message) {
        super(message);
    }
}
