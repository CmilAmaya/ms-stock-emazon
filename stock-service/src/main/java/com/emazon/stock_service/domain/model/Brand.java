package com.emazon.stock_service.domain.model;

import com.emazon.stock_service.domain.exception.EmptyFieldException;
import com.emazon.stock_service.domain.exception.InvalidBrandDescriptionException;
import com.emazon.stock_service.domain.exception.InvalidBrandNameException;
import com.emazon.stock_service.domain.utils.DomainConstants;

import static java.util.Objects.requireNonNull;

public class Brand {
    private Long id;
    private String name;
    private String description;

    public Brand(Long id, String name, String description) {
        if(name == null || name.trim().isEmpty()){
            throw new EmptyFieldException(DomainConstants.FIELD_NAME_NULL_MESSAGE);
        }
        if(name.length() > DomainConstants.MAXIMUM_LENGTH_NAME ){
            throw new InvalidBrandNameException(DomainConstants.FIELD_NAME_LENGTH_MESSAGE);
        }
        if(description == null || description.trim().isEmpty()){
            throw new EmptyFieldException(DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        }
        if(description.length() > DomainConstants.MAXIMUM_LENGTH_DESCRIPTION_BRAND){
            throw new InvalidBrandDescriptionException(DomainConstants.FIELD_DESCRIPTION_LENGTH_MESSAGE_BRAND);
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
