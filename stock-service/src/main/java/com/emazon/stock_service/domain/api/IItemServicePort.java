package com.emazon.stock_service.domain.api;

import com.emazon.stock_service.domain.model.Item;


public interface IItemServicePort {
    void createItem(Item item);
    void deleteItem(String name);
    Item getItem(String name);
}
