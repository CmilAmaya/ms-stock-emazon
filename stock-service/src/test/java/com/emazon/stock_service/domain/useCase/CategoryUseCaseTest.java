package com.emazon.stock_service.domain.useCase;

import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_service.domain.exception.CategoryNotFoundException;
import com.emazon.stock_service.domain.exception.InvalidCategoryNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.emazon.stock_service.domain.utils.DomainConstants;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class CategoryUseCaseTest {

    private ICategoryPersistencePort categoryPersistencePort;
    private CategoryUseCase categoryUseCase;

    @BeforeEach
    public void setUp() {
        categoryPersistencePort = mock(ICategoryPersistencePort.class);
        categoryUseCase = new CategoryUseCase(categoryPersistencePort);
    }

    @Test
     void testCreateCategorySuccessfully() {
        // Arrange
        Category category = new Category(null, "Electrónica", "Categoría para electrónicos");

        when(categoryPersistencePort.findByName(category.getName())).thenReturn(Optional.empty());

        // Act
        categoryUseCase.createCategory(category);

        // Assert
        verify(categoryPersistencePort).save(category);
    }

    @Test
     void testCreateCategoryWithExistingName() {
        // Arrange
        Category category = new Category(null, "Electrónica", "Categoría para electrónicos");

        when(categoryPersistencePort.findByName(category.getName())).thenReturn(Optional.of(category));

        // Act & Assert
        InvalidCategoryNameException exception = assertThrows(
                InvalidCategoryNameException.class,
                () -> categoryUseCase.createCategory(category)
        );

        assertEquals(DomainConstants.CATEGORY_ALREADY_EXISTS_MESSAGE, exception.getMessage());
        verify(categoryPersistencePort, never()).save(category);
    }

    @Test
     void testDeleteCategorySuccessfully() {
        // Arrange
        String categoryName = "Electrónica";
        Category category = new Category(null, categoryName, "Categoría para electrónicos");

        when(categoryPersistencePort.findByName(categoryName)).thenReturn(Optional.of(category));

        // Act
        categoryUseCase.deleteCategory(categoryName);

        // Assert
        verify(categoryPersistencePort).delete(categoryName);
    }

    @Test
     void testDeleteCategoryNotFound() {
        // Arrange
        String categoryName = "Electrónica";

        when(categoryPersistencePort.findByName(categoryName)).thenReturn(Optional.empty());

        // Act & Assert
        CategoryNotFoundException exception = assertThrows(
                CategoryNotFoundException.class,
                () -> categoryUseCase.deleteCategory(categoryName)
        );

        assertEquals(DomainConstants.CATEGORY_NOT_FOUND, exception.getMessage());
        verify(categoryPersistencePort, never()).delete(categoryName);
    }
}
