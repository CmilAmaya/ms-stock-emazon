package com.emazon.stock_service.adapters.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BrandRequest {
    private final String name;
    private final String description;
}
