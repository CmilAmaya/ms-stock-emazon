package com.emazon.stock_service.domain.spi;

import com.emazon.stock_service.domain.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICategoryPersistencePort {
    void save(Category category);
    void delete(String name);
    Optional<Category> findByName(String name);
    Page<Category> findAllCategories(Pageable pageable);
}
