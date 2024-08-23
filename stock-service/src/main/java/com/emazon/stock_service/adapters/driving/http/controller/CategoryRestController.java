package com.emazon.stock_service.adapters.driving.http.controller;

import com.emazon.stock_service.adapters.driving.http.dto.request.CategoryRequest;
import com.emazon.stock_service.adapters.driving.http.dto.response.CategoryResponse;
import com.emazon.stock_service.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.emazon.stock_service.adapters.driving.http.mapper.ICategoryResponseMapper;
import com.emazon.stock_service.domain.api.ICategoryServicePort;
import com.emazon.stock_service.domain.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @GetMapping("/all")
    public ResponseEntity<List<CategoryResponse>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sortOrder) {

        boolean ascending = "asc".equalsIgnoreCase(sortOrder);
        Page<Category> categoryPage = categoryServicePort.getAllCategories(page, size, ascending);
        List<CategoryResponse> cartegoryResponseList = categoryPage.getContent()
                .stream()
                .map(categoryResponseMapper::toCategoryResponse)
                .collect(Collectors.toList());

        return new ResponseEntity<>(cartegoryResponseList, HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("name") String name) {
        categoryServicePort.deleteCategory(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
