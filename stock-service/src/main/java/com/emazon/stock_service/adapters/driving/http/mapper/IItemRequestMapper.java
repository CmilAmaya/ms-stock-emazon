package com.emazon.stock_service.adapters.driving.http.mapper;

import com.emazon.stock_service.adapters.driving.http.dto.request.ItemRequest;
import com.emazon.stock_service.adapters.driving.http.dto.response.ItemResponse;
import com.emazon.stock_service.adapters.driving.http.dto.response.CategoryResponse;
import com.emazon.stock_service.adapters.driving.http.dto.response.BrandResponse;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IItemRequestMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "categoriesIds", source = "itemRequest.categoriesIds"),
            @Mapping(target = "brand", source = "itemRequest.brandId", qualifiedByName = "mapBrandIdToBrand")
    })
    Item toItem(ItemRequest itemRequest);

    @Mappings({
            @Mapping(target = "categories", source = "item.categoriesIds", qualifiedByName = "mapCategoryIdsToCategoryResponses"),
            @Mapping(target = "brand", source = "item.brand", qualifiedByName = "mapBrandToBrandResponse")
    })
    ItemResponse toItemResponse(Item item);

    @Named("mapBrandIdToBrand")
    default Brand mapBrandIdToBrand(Long brandId) {
        if (brandId == null) {
            return null;
        }
        Brand brand = new Brand();
        brand.setId(brandId);
        return brand;
    }

    @Named("mapCategoryIdsToCategoryResponses")
    default List<CategoryResponse> mapCategoryIdsToCategoryResponses(List<Long> categoriesIds) {
        if (categoriesIds == null) {
            return null;
        }
        return categoriesIds.stream()
                .map(categoryId -> new CategoryResponse(categoryId, null, null))
                .collect(Collectors.toList());
    }

    @Named("mapBrandToBrandResponse")
    default BrandResponse mapBrandToBrandResponse(Brand brand) {
        if (brand == null) {
            return null;
        }
        return new BrandResponse(brand.getId(), null, null);
    }
}
