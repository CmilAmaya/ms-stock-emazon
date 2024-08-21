package com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.mapper;

import com.emazon.stock_service.domain.model.Category;

import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICategoryEntityMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    CategoryEntity toEntity(Category category);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Category toDomainModel(CategoryEntity categoryEntity);
}
