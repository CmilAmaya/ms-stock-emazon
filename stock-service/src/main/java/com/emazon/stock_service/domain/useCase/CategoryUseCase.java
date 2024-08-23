package com.emazon.stock_service.domain.useCase;

import com.emazon.stock_service.domain.api.ICategoryServicePort;
import com.emazon.stock_service.domain.exception.CategoryNotFoundException;
import com.emazon.stock_service.domain.exception.InvalidCategoryNameException;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_service.domain.utils.DomainConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class CategoryUseCase implements ICategoryServicePort {
    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }
    @Override
    public void createCategory(Category category) {
        if(categoryPersistencePort.findByName(category.getName()).isPresent()) {
erger            throw new InvalidCategoryNameException(DomainConstants.CATEGORY_ALREADY_EXISTS_MESSAGE);
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
    public Page<Category> getAllCategories(int page, int size, boolean ascending) {
        Sort sort = ascending ? Sort.by("name").ascending() : Sort.by("name").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return categoryPersistencePort.findAllCategories(pageable);
    }
}
