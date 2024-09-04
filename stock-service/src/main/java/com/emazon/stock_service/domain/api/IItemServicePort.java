package com.emazon.stock_service.domain.api;

import com.emazon.stock_service.adapters.driving.http.dto.response.ItemResponse;
import com.emazon.stock_service.adapters.driving.http.dto.response.PaginatedResponse;
import com.emazon.stock_service.domain.model.Item;


public interface IItemServicePort {
    void createItem(Item item);
    void deleteItem(String name);
    Item getItem(String name);
    PaginatedResponse<ItemResponse> getAllItems(int page, int size, String sortBy, boolean ascending);
}
