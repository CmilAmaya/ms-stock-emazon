package com.emazon.stock_service.domain.useCase;

import com.emazon.stock_service.domain.api.IItemServicePort;
import com.emazon.stock_service.domain.exception.BrandNotFoundException;
import com.emazon.stock_service.domain.exception.InvalidCategoryNumberException;
import com.emazon.stock_service.domain.exception.ItemNotFoundException;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.model.Item;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import com.emazon.stock_service.domain.spi.IItemPersistencePort;
import com.emazon.stock_service.domain.utils.DomainConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemUseCaseTest {

    @Mock
    private IItemPersistencePort iItemPersistencePort;

    @Mock
    private IBrandPersistencePort iBrandPersistencePort;

    @InjectMocks
    private ItemUseCase itemUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateItem_Success() {
        // Arrange
        Item item = new Item();
        item.setCategoriesIds(Collections.singletonList(1L));
        item.setBrand(new Brand(1L, "TestBrand", "TestDescription"));

        when(iBrandPersistencePort.findById(1L)).thenReturn(Optional.of(new Brand(1L, "TestBrand", "TestDescription")));

        // Act
        itemUseCase.createItem(item);

        // Assert
        verify(iItemPersistencePort, times(1)).save(item);
    }

    @Test
    void testCreateItem_InvalidCategoryNumber() {
        // Arrange
        Item item = new Item();
        item.setCategoriesIds(Collections.nCopies(DomainConstants.MAXIMUM_CATEGORY_NUMBER + 1, 1L));
        item.setBrand(new Brand(1L, "TestBrand", "TestDescription"));

        when(iBrandPersistencePort.findById(1L)).thenReturn(Optional.of(new Brand(1L, "TestBrand", "TestDescription")));

        // Act & Assert
        InvalidCategoryNumberException exception = assertThrows(InvalidCategoryNumberException.class, () -> {
            itemUseCase.createItem(item);
        });
        assertEquals(DomainConstants.FIELD_CATEGORIES_NUMBER_MESSAGE, exception.getMessage());
    }

    @Test
    void testCreateItem_BrandNotFound() {
        // Arrange
        Item item = new Item();
        item.setCategoriesIds(Collections.singletonList(1L));
        item.setBrand(new Brand(1L, "TestBrand", "TestDescription"));

        when(iBrandPersistencePort.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        BrandNotFoundException exception = assertThrows(BrandNotFoundException.class, () -> {
            itemUseCase.createItem(item);
        });
        assertEquals(DomainConstants.BRAND_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testDeleteItem_Success() {
        // Arrange
        String itemName = "TestItem";
        when(iItemPersistencePort.findByName(itemName)).thenReturn(Optional.of(new Item()));

        // Act
        itemUseCase.deleteItem(itemName);

        // Assert
        verify(iItemPersistencePort, times(1)).delete(itemName);
    }

    @Test
    void testDeleteItem_ItemNotFound() {
        // Arrange
        String itemName = "TestItem";
        when(iItemPersistencePort.findByName(itemName)).thenReturn(Optional.empty());

        // Act & Assert
        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class, () -> {
            itemUseCase.deleteItem(itemName);
        });
        assertEquals(DomainConstants.ITEM_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testGetItem_Success() {
        // Arrange
        String itemName = "TestItem";
        Item item = new Item();
        when(iItemPersistencePort.findByName(itemName)).thenReturn(Optional.of(item));

        // Act
        Item result = itemUseCase.getItem(itemName);

        // Assert
        assertNotNull(result);
        assertEquals(item, result);
    }

    @Test
    void testGetItem_ItemNotFound() {
        // Arrange
        String itemName = "TestItem";
        when(iItemPersistencePort.findByName(itemName)).thenReturn(Optional.empty());

        // Act & Assert
        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class, () -> {
            itemUseCase.getItem(itemName);
        });
        assertEquals(DomainConstants.ITEM_NOT_FOUND, exception.getMessage());
    }
}
