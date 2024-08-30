package com.emazon.stock_service.domain.useCase;

import com.emazon.stock_service.domain.api.IItemServicePort;
import com.emazon.stock_service.domain.exception.InvalidCategoryNumberException;
import com.emazon.stock_service.domain.exception.InvalidItemNameException;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.model.Item;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import com.emazon.stock_service.domain.spi.IItemPersistencePort;
import com.emazon.stock_service.domain.utils.DomainConstants;

public class ItemUseCase implements IItemServicePort {
    public IItemPersistencePort iItemPersistencePort;
    public IBrandPersistencePort iBrandPersistencePort;
    public ItemUseCase(IItemPersistencePort iItemPersistencePort, IBrandPersistencePort iBrandPersistencePort) {
        this.iItemPersistencePort = iItemPersistencePort;
        this.iBrandPersistencePort = iBrandPersistencePort;
    }
    @Override
    public void createItem(Item item) {
        if (item.getCategoriesIds() == null || item.getCategoriesIds().isEmpty()) {
            throw new IllegalArgumentException("At least one category must be associated.");
        }
        if (item.getCategoriesIds().size() > DomainConstants.MAXIMUM_CATEGORY_NUMBER) {
            throw new InvalidCategoryNumberException("The number of categories exceeds the maximum allowed.");
        }
        Brand brand = iBrandPersistencePort.findById(item.getBrand().getId())
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));
        item.setBrand(brand);
        iItemPersistencePort.save(item);
    }

    @Override
    public void deleteItem(String name) {
        if(iItemPersistencePort.findByName(name).isEmpty()) {
            throw new InvalidItemNameException(DomainConstants.ITEM_NOT_FOUND);
        }
        iItemPersistencePort.delete(name);
    }

    @Override
    public Item getItem(String name) {
        return iItemPersistencePort.findByName(name)
                .orElseThrow(() -> new InvalidItemNameException(DomainConstants.ITEM_NOT_FOUND));
    }
}
