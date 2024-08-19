package com.emazon.stock_service.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CategoryResponse {
    private final Long id;
    private final String name;
    private final String description;
}
