package com.emazon.stock_service.domain.utils;

public class DomainConstants {
    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String FIELD_NAME_NULL_MESSAGE = "Field 'name' cannot be null";
    public static final String FIELD_NAME_LENGTH_MESSAGE = "Field 'name' must be between 1 and 50 characters";
    public static final String FIELD_DESCRIPTION_NULL_MESSAGE = "Field 'description' cannot be null";
    public static final String FIELD_DESCRIPTION_LENGTH_MESSAGE_CATEGORY = "Field 'description' must be between 1 and 90 characters";
    public static final String FIELD_DESCRIPTION_LENGTH_MESSAGE_BRAND = "Field 'description' must be between 1 and 120 characters";
    public static final String CATEGORY_ALREADY_EXISTS_MESSAGE = "Category already exists.";
    public static final String BRAND_ALREADY_EXISTS_MESSAGE = "Brand already exists.";
    public static final String CATEGORY_NOT_FOUND = "Category not found.";
    public static final String BRAND_NOT_FOUND = "Brand not found.";
    public static final int MAXIMUM_LENGTH_NAME = 50;
    public static final int MAXIMUM_LENGTH_DESCRIPTION_CATEGORY = 90;
    public static final int MAXIMUM_LENGTH_DESCRIPTION_BRAND = 120;
    public static final  String FIELD_CATEGORIES_NULL_MESSAGE = "Field 'categories' must have at least one category";
    public static final String FIELD_CATEGORIES_NUMBER_MESSAGE = "Field 'categories' must have at most 3 categories";
    public static final String ITEM_ALREADY_EXISTS_MESSAGE = "Item already exists.";
    public static final String ITEM_NOT_FOUND = "Item not found.";
    public static final int MAXIMUM_CATEGORY_NUMBER = 3;
}
