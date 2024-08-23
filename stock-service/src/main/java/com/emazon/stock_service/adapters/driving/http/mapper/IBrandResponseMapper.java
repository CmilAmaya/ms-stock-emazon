package com.emazon.stock_service.adapters.driving.http.mapper;

import com.emazon.stock_service.adapters.driving.http.dto.response.BrandResponse;
import com.emazon.stock_service.domain.model.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IBrandResponseMapper {
    BrandResponse toBrandResponse(Brand brand);
}
