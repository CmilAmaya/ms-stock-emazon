package com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String message) {
        super(message);
    }
}
