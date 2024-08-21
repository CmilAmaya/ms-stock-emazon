package com.emazon.stock_service.domain.api;

import com.emazon.stock_service.domain.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICategoryServicePort {
    void createCategory(Category category);
    void deleteCategory(String name);
    Category getCategory(String name);
    Page<Category> getAllCategories(int page, int size, boolean ascending);

}
