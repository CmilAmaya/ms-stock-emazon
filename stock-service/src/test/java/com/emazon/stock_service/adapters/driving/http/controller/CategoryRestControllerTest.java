package com.emazon.stock_service.adapters.driving.http.controller;

import com.emazon.stock_service.adapters.driving.http.dto.request.CategoryRequest;
import com.emazon.stock_service.adapters.driving.http.dto.response.CategoryResponse;
import com.emazon.stock_service.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.emazon.stock_service.adapters.driving.http.mapper.ICategoryResponseMapper;
import com.emazon.stock_service.domain.api.ICategoryServicePort;
import com.emazon.stock_service.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CategoryRestControllerTest {

    @InjectMocks
    private CategoryRestController categoryRestController;

    @Mock
    private ICategoryServicePort categoryServicePort;

    @Mock
    private ICategoryRequestMapper categoryRequestMapper;

    @Mock
    private ICategoryResponseMapper categoryResponseMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategory() {
        // Arrange
        CategoryRequest categoryRequest = new CategoryRequest("TestCategory", "TestDescription");
        Category category = new Category();
        category.setName("TestCategory");
        category.setDescription("TestDescription");

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setName("TestCategory");
        categoryResponse.setDescription("TestDescription");

        when(categoryRequestMapper.toCategory(categoryRequest)).thenReturn(category);
        when(categoryResponseMapper.toCategoryResponse(category)).thenReturn(categoryResponse);

        // Act
        ResponseEntity<CategoryResponse> response = categoryRestController.createCategory(categoryRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(categoryResponse, response.getBody());

        verify(categoryServicePort, times(1)).createCategory(category);
        verify(categoryRequestMapper, times(1)).toCategory(categoryRequest);
        verify(categoryResponseMapper, times(1)).toCategoryResponse(category);
    }
}
