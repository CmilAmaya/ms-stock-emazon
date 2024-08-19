package com.emazon.stock_service.infrastucture.in;

import com.emazon.stock_service.application.dto.request.CategoryRequest;
import com.emazon.stock_service.application.dto.response.CategoryResponse;
import com.emazon.stock_service.application.mapper.ICategoryRequestMapper;
import com.emazon.stock_service.application.mapper.ICategoryResponseMapper;
import com.emazon.stock_service.domain.api.ICategoryServicePort;
import com.emazon.stock_service.domain.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryRestController {

    @Autowired
    private ICategoryServicePort categoryServicePort;

    @Autowired
    private ICategoryRequestMapper categoryRequestMapper;

    @Autowired
    private ICategoryResponseMapper categoryResponseMapper;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        Category category = categoryRequestMapper.toCategory(categoryRequest);
        categoryServicePort.createCategory(category);
        CategoryResponse response = categoryResponseMapper.toCategoryResponse(category);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
