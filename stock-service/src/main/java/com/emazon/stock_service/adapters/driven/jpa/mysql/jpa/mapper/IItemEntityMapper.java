package com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.mapper;

import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.entity.BrandEntity;
import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.entity.CategoryEntity;
import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.entity.ItemCategoryEntity;
import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.entity.ItemEntity;
import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.repository.IBrandRepository;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class IItemEntityMapper {

    @Autowired
    private IBrandRepository brandRepository;

    @Mappings({
            @Mapping(source = "brand.id", target = "brand"),
            @Mapping(target = "categoriesIds", expression = "java(mapCategoriesToIds(itemEntity.getItemCategories()))")
    })
    public abstract Item toDomainModel(ItemEntity itemEntity);

    @Mappings({
            @Mapping(source = "brand", target = "brand.id"),
            @Mapping(target = "itemCategories", expression = "java(mapCategoryIds(item.getCategoriesIds(), itemEntity))")
    })
    public abstract ItemEntity toItemEntity(Item item);

    public List<Long> mapCategoriesToIds(List<ItemCategoryEntity> itemCategories) {
        return itemCategories.stream()
                .map(itemCategory -> itemCategory.getCategory().getId())
                .collect(Collectors.toList());
    }

    public List<ItemCategoryEntity> mapCategoryIds(List<Long> categoryIds, ItemEntity itemEntity) {
        return categoryIds.stream()
                .map(categoryId -> {
                    ItemCategoryEntity itemCategoryEntity = new ItemCategoryEntity();
                    itemCategoryEntity.setItem(itemEntity);
                    CategoryEntity categoryEntity = new CategoryEntity();
                    categoryEntity.setId(categoryId);
                    itemCategoryEntity.setCategory(categoryEntity);
                    return itemCategoryEntity;
                })
                .collect(Collectors.toList());
    }

    public Brand map(Long brandId) {
        if (brandId == null) {
            return null;
        }
        BrandEntity brandEntity = brandRepository.findById(brandId).orElse(null);
        if (brandEntity == null) {
            return null;
        }
        Brand brand = new Brand();
        brand.setId(brandEntity.getId());
        brand.setName(brandEntity.getName());
        brand.setDescription(brandEntity.getDescription());
        System.out.println("ACA ESTAMOS:" + brand.getName());
        return brand;
    }

    public Long map(Brand brand) {
        if (brand == null) {
            return null;
        }
        return brand.getId();
    }
}