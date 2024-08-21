package com.emazon.stock_service.domain.useCase;

import com.emazon.stock_service.domain.api.ICategoryServicePort;
import com.emazon.stock_service.domain.exception.CategoryNotFoundException;
import com.emazon.stock_service.domain.exception.InvalidCategoryNameException;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_service.domain.utils.DomainConstants;
import java.util.List;

public class CategoryUseCase implements ICategoryServicePort {
    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }
    @Override
    public void createCategory(Category category) {
        if(categoryPersistencePort.findByName(category.getName()).isPresent()) {
            throw new InvalidCategoryNameException(DomainConstants.CATEGORY_ALREADY_EXISTS_MESSAGE);
        }
        categoryPersistencePort.save(category);
    }
    @Override
    public void deleteCategory(String name) {
        categoryPersistencePort.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException(DomainConstants.CATEGORY_NOT_FOUND));
        categoryPersistencePort.delete(name);
    }
    @Override
    public Category getCategory(String name) {
        return categoryPersistencePort.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException(DomainConstants.CATEGORY_NOT_FOUND));
    }
    @Override
    public List<Category> getAllCategoriesAscending(int page, int size) {
        return categoryPersistencePort.findAllCategories(page, size, true);
    }
    @Override
    public List<Category> getAllCategoriesDescending(int page, int size) {
        return categoryPersistencePort.findAllCategories(page, size, false);
    }
}
