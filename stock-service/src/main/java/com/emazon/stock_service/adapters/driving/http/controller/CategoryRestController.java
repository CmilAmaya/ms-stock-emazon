package com.emazon.stock_service.adapters.driving.http.controller;

import com.emazon.stock_service.adapters.driving.http.dto.request.CategoryRequest;
import com.emazon.stock_service.adapters.driving.http.dto.response.CategoryResponse;
import com.emazon.stock_service.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.emazon.stock_service.adapters.driving.http.mapper.ICategoryResponseMapper;
import com.emazon.stock_service.domain.api.ICategoryServicePort;
import com.emazon.stock_service.domain.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{name}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable("name") String name ) {
        Category category = categoryServicePort.getCategory(name);
        CategoryResponse response = categoryResponseMapper.toCategoryResponse(category);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all/asc")
    public ResponseEntity<List<CategoryResponse>> getAllCategoriesAscending(
            @RequestParam int page, @RequestParam int size) {
        List<Category> categories = categoryServicePort.getAllCategoriesAscending(page, size);
        List<CategoryResponse> responses = categories.stream()
                .map(categoryResponseMapper::toCategoryResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/all/desc")
    public ResponseEntity<List<CategoryResponse>> getAllCategoriesDescending(
            @RequestParam int page, @RequestParam int size) {
        List<Category> categories = categoryServicePort.getAllCategoriesDescending(page, size);
        List<CategoryResponse> responses = categories.stream()
                .map(categoryResponseMapper::toCategoryResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("name") String name) {
        categoryServicePort.deleteCategory(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
