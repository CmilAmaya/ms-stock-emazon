package com.emazon.stock_service.domain.model;

import com.emazon.stock_service.domain.exception.EmptyFieldException;
import com.emazon.stock_service.domain.exception.InvalidCategoryDescriptionException;
import com.emazon.stock_service.domain.exception.InvalidCategoryNameException;
import com.emazon.stock_service.domain.utils.DomainConstants;

import static java.util.Objects.requireNonNull;

public class Category {
    private Long id;
    private String name;
    private String description;

    public Category(Long id, String name, String description) {
        if (name == null || name.trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.FIELD_NAME_NULL_MESSAGE);
        }
        if (name.length() > DomainConstants.MINIMUM_LENGTH) {
            throw new InvalidCategoryNameException(DomainConstants.FIELD_NAME_LENGTH_MESSAGE);
        }
        if (description == null || description.trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        }
        if (description.length() > DomainConstants.MAXIMUM_LENGTH) {
            throw new InvalidCategoryDescriptionException(DomainConstants.FIELD_DESCRIPTION_LENGTH_MESSAGE);
        }
        this.id = id;
        this.name = requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        this.description = requireNonNull(description, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
