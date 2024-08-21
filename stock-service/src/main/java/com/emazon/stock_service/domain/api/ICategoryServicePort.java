package com.emazon.stock_service.domain.api;

import com.emazon.stock_service.domain.model.Category;
import java.util.List;

public interface ICategoryServicePort {
    void createCategory(Category category);
    void deleteCategory(String name);
    Category getCategory(String name);
    List<Category> getAllCategoriesAscending(int page, int size);
    List<Category> getAllCategoriesDescending(int page, int size);
}
