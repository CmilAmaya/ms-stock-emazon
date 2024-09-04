package com.emazon.stock_service.domain.spi;

import com.emazon.stock_service.domain.model.Item;

import java.util.List;
import java.util.Optional;

public interface IItemPersistencePort {
    void save(Item item);
    void delete(String name);
    Optional<Item> findByName(String name);
    List<Item> findAllPaged(int page, int size, String sortBy, boolean ascending);
    int countItems();
}
