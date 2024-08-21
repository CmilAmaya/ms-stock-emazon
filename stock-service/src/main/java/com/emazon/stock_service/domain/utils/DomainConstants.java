package com.emazon.stock_service.domain.utils;

public class DomainConstants {
    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String FIELD_NAME_NULL_MESSAGE = "Field 'name' cannot be null";
    public static final String FIELD_NAME_LENGTH_MESSAGE = "Field 'name' must be between 1 and 50 characters";
    public static final String FIELD_DESCRIPTION_NULL_MESSAGE = "Field 'description' cannot be null";
    public static final String FIELD_DESCRIPTION_LENGTH_MESSAGE = "Field 'description' must be between 1 and 90 characters";
    public static final String CATEGORY_ALREADY_EXISTS_MESSAGE = "Category already exists.";
    public static final String CATEGORY_NOT_FOUND = "Category not found.";
}
