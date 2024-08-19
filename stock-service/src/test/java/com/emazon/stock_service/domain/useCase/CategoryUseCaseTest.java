package com.emazon.stock_service.domain.useCase;

import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_service.domain.exception.InvalidCategoryNameException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import com.emazon.stock_service.domain.utils.DomainConstants;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryUseCaseTest {

    private ICategoryPersistencePort categoryPersistencePort;
    private CategoryUseCase categoryUseCase;

    @BeforeEach
    public void setUp() {
        categoryPersistencePort = mock(ICategoryPersistencePort.class);
        categoryUseCase = new CategoryUseCase(categoryPersistencePort);
    }

    @Test
    public void testCreateCategorySuccessfully() {
        // Arrange
        Category category = new Category(null, "Electrónica", "Categoría para electrónicos");

        when(categoryPersistencePort.findByName(category.getName())).thenReturn(Optional.empty());

        // Act
        categoryUseCase.createCategory(category);

        // Assert
        verify(categoryPersistencePort).save(category);
    }

    @Test
    public void testCreateCategoryWithExistingName() {
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
}
