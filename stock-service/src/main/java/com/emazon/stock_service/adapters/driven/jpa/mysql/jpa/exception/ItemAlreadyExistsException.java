package com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.exception;

public class ItemAlreadyExistsException extends RuntimeException {
    public ItemAlreadyExistsException(String message) {
        super(message);
    }
}
