package com.emazon.stock_service.domain.useCase;

import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.repository.ICategoryRepository;
import com.emazon.stock_service.adapters.driving.http.dto.response.ItemResponse;
import com.emazon.stock_service.adapters.driving.http.dto.response.PaginatedResponse;
import com.emazon.stock_service.adapters.driving.http.mapper.IItemResponseMapper;
import com.emazon.stock_service.domain.api.IItemServicePort;
import com.emazon.stock_service.domain.exception.BrandNotFoundException;
import com.emazon.stock_service.domain.exception.InvalidCategoryNumberException;
import com.emazon.stock_service.domain.exception.ItemNotFoundException;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.model.Item;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import com.emazon.stock_service.domain.spi.IItemPersistencePort;
import com.emazon.stock_service.domain.utils.DomainConstants;

import java.util.*;
import java.util.stream.Collectors;

public class ItemUseCase implements IItemServicePort {
    public IItemPersistencePort iItemPersistencePort;
    public IBrandPersistencePort iBrandPersistencePort;
    public IItemResponseMapper itemResponseMapper;
    public ICategoryRepository categoryRepository;

    public ItemUseCase(IItemPersistencePort iItemPersistencePort, IBrandPersistencePort iBrandPersistencePort, IItemResponseMapper itemResponseMapper, ICategoryRepository categoryRepository) {
        this.iItemPersistencePort = iItemPersistencePort;
        this.iBrandPersistencePort = iBrandPersistencePort;
        this.itemResponseMapper = itemResponseMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void createItem(Item item) {
        if (item.getCategoriesIds() == null || item.getCategoriesIds().isEmpty()) {
            throw new InvalidCategoryNumberException(DomainConstants.FIELD_CATEGORIES_NULL_MESSAGE);
        }
        if (item.getCategoriesIds().size() > DomainConstants.MAXIMUM_CATEGORY_NUMBER) {
            throw new InvalidCategoryNumberException(DomainConstants.FIELD_CATEGORIES_NUMBER_MESSAGE);
        }
        Brand brand = iBrandPersistencePort.findById(item.getBrand().getId())
                .orElseThrow(() -> new BrandNotFoundException(DomainConstants.BRAND_NOT_FOUND));
        item.setBrand(brand);
        iItemPersistencePort.save(item);
    }

    @Override
    public void deleteItem(String name) {
        if (iItemPersistencePort.findByName(name).isEmpty()) {
            throw new ItemNotFoundException(DomainConstants.ITEM_NOT_FOUND);
        }
        iItemPersistencePort.delete(name);
    }

    @Override
    public Item getItem(String name) {
        return iItemPersistencePort.findByName(name)
                .orElseThrow(() -> new ItemNotFoundException(DomainConstants.ITEM_NOT_FOUND));
    }

    private List<String> fetchCategoryNamesByIds(List<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            return Collections.emptyList();
        }

        return categoryRepository.findNamesByIds(categoryIds);
    }

    @Override
    public PaginatedResponse<ItemResponse> getAllItems(int page, int size, String sortBy, boolean ascending) {
        List<Item> items;

        if ("itemCategories".equals(sortBy.trim())) {
            items = iItemPersistencePort.findAllPaged(page, size, "name", ascending);

            items.sort((item1, item2) -> {
                List<String> names1 = fetchCategoryNamesByIds(item1.getCategoriesIds());
                List<String> names2 = fetchCategoryNamesByIds(item2.getCategoriesIds());

                String minName1 = names1.stream().min(String::compareTo).orElse("");
                String minName2 = names2.stream().min(String::compareTo).orElse("");

                return ascending ? minName1.compareTo(minName2) : minName2.compareTo(minName1);
            });
        } else {
            items = iItemPersistencePort.findAllPaged(page, size, sortBy, ascending);
        }

        List<ItemResponse> itemResponses = items.stream()
                .map(item -> itemResponseMapper.toItemResponse(item))
                .collect(Collectors.toList());

        int totalItems = iItemPersistencePort.countItems();
        int totalPages = (int) Math.ceil((double) totalItems / size);

        return new PaginatedResponse<>(itemResponses, totalItems, totalPages, page, size);
    }
}