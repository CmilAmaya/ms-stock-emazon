package com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.mapper;

import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.entity.BrandEntity;
import com.emazon.stock_service.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IBrandEntityMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    BrandEntity toEntity(Brand brand);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Brand toDomainModel(BrandEntity brandEntity);
}
