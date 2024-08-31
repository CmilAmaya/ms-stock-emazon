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
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ItemJpaAdapter implements IItemPersistencePort {

    private final IItemEntityMapper itemEntityMapper;
    private final IItemRepository itemRepository;
    private final IItemCategoryRepository itemCategoryRepository;

    @Override
    @Transactional
    public void save(Item item) {
        if(itemRepository.findByName(item.getName()).isPresent()) {
            throw new ItemAlreadyExistsException(AdapterConstants.ITEM_ALREADY_EXISTS);
        }
        ItemEntity itemEntity = itemEntityMapper.toItemEntity(item);

        itemRepository.save(itemEntity);

        for (Long categoryId : item.getCategoriesIds()) {
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
}
