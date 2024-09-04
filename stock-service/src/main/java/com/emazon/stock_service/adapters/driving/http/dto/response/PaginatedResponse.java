package com.emazon.stock_service.adapters.driving.http.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaginatedResponse<T> {
    private List<T> items;
    private int totalItems;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public PaginatedResponse(List<T> items, int totalItems, int totalPages, int page, int size) {
        this.items = items;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.page = page;
        this.size = size;
        this.totalElements = totalItems;
    }
}
