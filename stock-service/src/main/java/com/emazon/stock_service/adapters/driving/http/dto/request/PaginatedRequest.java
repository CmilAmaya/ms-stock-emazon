package com.emazon.stock_service.adapters.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PaginatedRequest {
    private int page;
    private int size;
    private String sortBy;
    private String order;
}
