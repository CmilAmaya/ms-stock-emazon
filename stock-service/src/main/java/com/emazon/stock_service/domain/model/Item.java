package com.emazon.stock_service.domain.model;

import com.emazon.stock_service.domain.exception.EmptyFieldException;
import com.emazon.stock_service.domain.exception.InvalidCategoryNumberException;
import com.emazon.stock_service.domain.utils.DomainConstants;

import java.util.List;

public class Item {
    private Long id;
    private String name;
    private String description;
    private Long quantity;
    private Long price;
    private List<Long> categoriesIds;
    private Brand brand;

    public Item(Long id, String name, String description, Long quantity, Long price, List<Long> categoriesIds, Brand brand) {
        if(name == null || name.trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.FIELD_NAME_NULL_MESSAGE);
        }
        if(description == null || description.trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        }
        if(categoriesIds == null || categoriesIds.isEmpty()) {
            System.out.println("Categories received: " + categoriesIds);
            throw new EmptyFieldException(DomainConstants.FIELD_CATEGORIES_NULL_MESSAGE);
        }
        if(categoriesIds.size() > DomainConstants.MAXIMUM_CATEGORY_NUMBER) {
            throw new InvalidCategoryNumberException(DomainConstants.FIELD_CATEGORIES_NUMBER_MESSAGE);
        }
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.categoriesIds = categoriesIds;
        this.brand = brand;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public List<Long> getCategoriesIds() {
        return categoriesIds;
    }

    public void setCategoriesIds(List<Long> categoriesIds) {
        this.categoriesIds = categoriesIds;
    }

    public Brand getBrand() { return brand; }

    public void setBrand(Brand brand) { this.brand = brand; }
}
