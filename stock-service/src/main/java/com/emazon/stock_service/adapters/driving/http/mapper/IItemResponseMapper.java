package com.emazon.stock_service.adapters.driving.http.mapper;

import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.repository.ICategoryRepository;
import com.emazon.stock_service.adapters.driving.http.dto.response.ItemResponse;
import com.emazon.stock_service.adapters.driving.http.dto.response.CategoryResponse;
import com.emazon.stock_service.adapters.driving.http.dto.response.BrandResponse;
import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.entity.CategoryEntity;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class IItemResponseMapper {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Mappings({
            @Mapping(target = "categories", source = "categoriesIds"),
            @Mapping(target = "brand", source = "brand")
    })
    public abstract ItemResponse toItemResponse(Item item);

    public List<CategoryResponse> mapCategoriesToCategoryResponses(List<Long> categoryIds) {
        if (categoryIds == null) {
            return null;
        }
        return categoryIds.stream()
                .map(categoryId -> {
                    CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElse(null);
                    if (categoryEntity == null) {
                        return new CategoryResponse(categoryId, null, null);
                    }
                    return new CategoryResponse(categoryEntity.getId(), categoryEntity.getName(), categoryEntity.getDescription());
                })
                .collect(Collectors.toList());
    }

    public BrandResponse map(Brand brand) {
        if (brand == null) {
            return null;
        }
        return new BrandResponse(brand.getId(), brand.getName(), brand.getDescription());
    }
}