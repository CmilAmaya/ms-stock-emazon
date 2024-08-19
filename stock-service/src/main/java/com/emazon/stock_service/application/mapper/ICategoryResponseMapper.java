package com.emazon.stock_service.application.mapper;

import com.emazon.stock_service.application.dto.response.CategoryResponse;
import com.emazon.stock_service.domain.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICategoryResponseMapper {
    CategoryResponse toCategoryResponse(Category category);
    List<CategoryResponse> toCategoryResponseList(List<Category> categories);
}