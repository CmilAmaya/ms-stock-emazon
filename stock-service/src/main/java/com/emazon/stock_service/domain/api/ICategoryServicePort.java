package com.emazon.stock_service.domain.api;

import com.emazon.stock_service.domain.model.Category;
import java.util.List;

public interface ICategoryServicePort {
    void createCategory(Category category);
}
