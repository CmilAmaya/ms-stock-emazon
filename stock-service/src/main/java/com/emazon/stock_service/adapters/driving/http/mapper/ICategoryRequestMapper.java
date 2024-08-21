package com.emazon.stock_service.adapters.driving.http.mapper;

import com.emazon.stock_service.adapters.driving.http.dto.request.CategoryRequest;
import com.emazon.stock_service.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICategoryRequestMapper {

    @Mapping(target = "id", ignore = true)
    Category toCategory(CategoryRequest categoryRequest);
}
