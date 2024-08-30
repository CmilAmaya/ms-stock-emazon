package com.emazon.stock_service.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class CategoryResponse {
    private final Long id;
    private final String name;
    private final String description;

}
