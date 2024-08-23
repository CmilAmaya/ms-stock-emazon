package com.emazon.stock_service.domain.useCase;

import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_service.domain.exception.CategoryNotFoundException;
import com.emazon.stock_service.domain.exception.InvalidCategoryNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.emazon.stock_service.domain.utils.DomainConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
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

    @Test
    void testGetAllCategoriesSuccessfullyAscending() {
       // Arrange
       int page = 0;
       int size = 5;
       boolean ascending = true;

       Category category1 = new Category(null, "A", "Description A");
       Category category2 = new Category(null, "B", "Description B");

       Page<Category> categoryPage = new PageImpl<>(
               Arrays.asList(category1, category2),
               PageRequest.of(page, size, Sort.by("name").ascending()),
               2
       );

       when(categoryPersistencePort.findAllCategories(PageRequest.of(page, size, Sort.by("name").ascending()))).thenReturn(categoryPage);

       // Act
       Page<Category> result = categoryUseCase.getAllCategories(page, size, ascending);

       // Assert
       assertNotNull(result);
       assertEquals(2, result.getTotalElements());
       assertEquals("A", result.getContent().get(0).getName());
       assertEquals("B", result.getContent().get(1).getName());
    }

    @Test
    void testGetAllCategoriesSuccessfullyDescending() {
       // Arrange
       int page = 0;
       int size = 5;
       boolean ascending = false;

       Category category1 = new Category(null, "B", "Description B");
       Category category2 = new Category(null, "A", "Description A");

       Page<Category> categoryPage = new PageImpl<>(
               Arrays.asList(category1, category2),
               PageRequest.of(page, size, Sort.by("name").descending()),
               2
       );

       when(categoryPersistencePort.findAllCategories(PageRequest.of(page, size, Sort.by("name").descending()))).thenReturn(categoryPage);

       // Act
       Page<Category> result = categoryUseCase.getAllCategories(page, size, ascending);

       // Assert
       assertNotNull(result);
       assertEquals(2, result.getTotalElements());
       assertEquals("B", result.getContent().get(0).getName());
       assertEquals("A", result.getContent().get(1).getName());
    }

    @Test
    void testGetAllCategoriesPagination() {
       // Arrange
       int page = 1;
       int size = 5;
       boolean ascending = true;

       Category category1 = new Category(null, "C", "Description C");
       Category category2 = new Category(null, "D", "Description D");

       Page<Category> categoryPage = new PageImpl<>(
               Arrays.asList(category1, category2),
               PageRequest.of(page, size, Sort.by("name").ascending()),
               10
       );

       when(categoryPersistencePort.findAllCategories(PageRequest.of(page, size, Sort.by("name").ascending()))).thenReturn(categoryPage);

       // Act
       Page<Category> result = categoryUseCase.getAllCategories(page, size, ascending);

       // Assert
       assertNotNull(result);
       assertEquals(10, result.getTotalElements());
       assertEquals(2, result.getNumberOfElements());
       assertEquals(category1, result.getContent().get(0));
       assertEquals(category2, result.getContent().get(1));
    }
 }
