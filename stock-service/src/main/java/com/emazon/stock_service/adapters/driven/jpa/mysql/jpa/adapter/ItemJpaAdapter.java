package com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.adapter;

import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.entity.CategoryEntity;
import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.entity.ItemCategoryEntity;
import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.entity.ItemEntity;
import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.exception.ItemAlreadyExistsException;
import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.exception.ItemNotFoundException;
import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.mapper.IItemEntityMapper;
import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.repository.IItemCategoryRepository;
import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.repository.IItemRepository;
import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.utils.AdapterConstants;
import com.emazon.stock_service.domain.model.Item;
import com.emazon.stock_service.domain.spi.IItemPersistencePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ItemJpaAdapter implements IItemPersistencePort {

    private final IItemEntityMapper itemEntityMapper;
    private final IItemRepository itemRepository;
    private final IItemCategoryRepository itemCategoryRepository;

    @Override
    @Transactional
    public void save(Item item) {
        if (itemRepository.findByName(item.getName()).isPresent()) {
            throw new ItemAlreadyExistsException(AdapterConstants.ITEM_ALREADY_EXISTS);
        }
        ItemEntity itemEntity = itemEntityMapper.toItemEntity(item);

        itemRepository.save(itemEntity);

        // Use a Set to avoid duplicate category IDs
        Set<Long> uniqueCategoryIds = new HashSet<>(item.getCategoriesIds());
        for (Long categoryId : uniqueCategoryIds) {
            ItemCategoryEntity itemCategoryEntity = new ItemCategoryEntity();
            itemCategoryEntity.setItem(itemEntity);
            itemCategoryEntity.setCategory(new CategoryEntity(categoryId));

            itemCategoryRepository.save(itemCategoryEntity);
        }
    }

    @Override
    @Transactional
    public void delete(String name) {
        itemRepository.findByName(name)
                .orElseThrow(() -> new ItemNotFoundException(AdapterConstants.ITEM_NOT_FOUND));

        itemRepository.deleteByName(name);
    }

    @Override
    public Optional<Item> findByName(String name) {
        return itemRepository.findByName(name)
                .map(itemEntityMapper::toDomainModel);
    }

    @Override
    @Transactional
    public List<Item> findAllPaged(int page, int size, String sortBy, boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ItemEntity> itemPage = itemRepository.findAll(pageable, sortBy);
        return itemPage.getContent()
                .stream()
                .map(itemEntityMapper::toDomainModel)
                .collect(Collectors.toList());
    }



    @Override
    public int countItems() {
        return (int) itemRepository.count();
    }
}
