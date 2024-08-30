package com.emazon.stock_service.domain.model;

public class ItemCategory {
    private Long id;
    private String name;

    public ItemCategory(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ItemCategory() {
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
}
