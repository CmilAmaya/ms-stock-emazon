package com.emazon.stock_service.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BrandResponse {
    private final Long id;
    private final String name;
    private final String description;
}
