package com.emazon.stock_service.adapters.driving.http.mapper;

import com.emazon.stock_service.adapters.driving.http.dto.request.BrandRequest;
import com.emazon.stock_service.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IBrandRequestMapper {
    @Mapping(target = "id", ignore = true)
    Brand toBrand(BrandRequest brandRequest);
}
