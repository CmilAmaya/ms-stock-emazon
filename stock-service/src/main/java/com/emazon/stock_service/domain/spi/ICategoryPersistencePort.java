package com.emazon.stock_service.domain.spi;

import com.emazon.stock_service.domain.model.Category;
import java.util.List;
import java.util.Optional;

public interface ICategoryPersistencePort {
    void save(Category category);
    Optional<Category> findByName(String name);
}
