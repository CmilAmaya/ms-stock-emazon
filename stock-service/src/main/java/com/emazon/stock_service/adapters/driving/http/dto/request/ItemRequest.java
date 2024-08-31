package com.emazon.stock_service.adapters.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class ItemRequest {
    private final String name;
    private final String description;
    private final Long quantity;
    private final Long price;
    private final List<Long> categoriesIds;
    private final Long brandId;
}
