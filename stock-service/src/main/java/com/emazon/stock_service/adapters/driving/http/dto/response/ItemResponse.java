package com.emazon.stock_service.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class ItemResponse {
    private final Long id;
    private final String name;
    private final String description;
    private final Long quantity;
    private final Long price;
    private final List<CategoryResponse> categories;
    private final BrandResponse brand;
}
