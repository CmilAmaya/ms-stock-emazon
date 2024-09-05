package com.emazon.stock_service.domain.useCase;

import com.emazon.stock_service.adapters.driving.http.dto.response.BrandResponse;
import com.emazon.stock_service.adapters.driving.http.dto.response.CategoryResponse;
import com.emazon.stock_service.adapters.driving.http.dto.response.ItemResponse;
import com.emazon.stock_service.adapters.driving.http.dto.response.PaginatedResponse;
import com.emazon.stock_service.adapters.driving.http.mapper.IItemResponseMapper;
import com.emazon.stock_service.domain.exception.BrandNotFoundException;
import com.emazon.stock_service.domain.exception.InvalidCategoryNumberException;
import com.emazon.stock_service.domain.exception.ItemNotFoundException;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.model.Item;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import com.emazon.stock_service.domain.spi.IItemPersistencePort;
import com.emazon.stock_service.domain.utils.DomainConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemUseCaseTest {

    @Mock
    private IItemPersistencePort iItemPersistencePort;

    @Mock
    private IBrandPersistencePort iBrandPersistencePort;

    @Mock
    private IItemResponseMapper itemResponseMapper;

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


    @Test
    void testGetAllItems_Success() {
        // Arrange
        int page = 1;
        int size = 10;
        String sortBy = "name";
        boolean ascending = true;

        Brand brand = new Brand(1L, "TestBrand", "TestDescription");

        // Mock items with categories
        List<Item> mockItems = Arrays.asList(
                new Item(1L, "Item 1", "Description 1", 10L, 100L, Arrays.asList(1L, 2L), brand),
                new Item(2L, "Item 2", "Description 2", 5L, 50L, Arrays.asList(3L), brand)
        );

        // Mock categories
        CategoryResponse categoryResponse1 = new CategoryResponse(1L, "Category 1", "Description 1");
        CategoryResponse categoryResponse2 = new CategoryResponse(2L, "Category 2", "Description 2");
        CategoryResponse categoryResponse3 = new CategoryResponse(3L, "Category 3", "Description 3");

        BrandResponse brandResponse1 = new BrandResponse(1L, "TestBrand", "TestDescription");
        BrandResponse brandResponse2 = new BrandResponse(2L, "TestBrand2", "TestDescription2");

        when(iItemPersistencePort.findAllPaged(page, size, sortBy, ascending)).thenReturn(mockItems);
        when(iItemPersistencePort.countItems()).thenReturn(20);

        // Mocking the mapping to ItemResponse with CategoryResponse
        when(itemResponseMapper.toItemResponse(any(Item.class)))
                .thenReturn(new ItemResponse(1L, "Item 1", "Description 1", 10L, 100L, Arrays.asList(categoryResponse1, categoryResponse2), brandResponse1))
                .thenReturn(new ItemResponse(2L, "Item 2", "Description 2", 5L, 50L, Arrays.asList(categoryResponse3), brandResponse2));

        // Act
        PaginatedResponse<ItemResponse> response = itemUseCase.getAllItems(page, size, sortBy, ascending);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.getItems().size());
        assertEquals(20, response.getTotalItems());
        assertEquals(2, response.getTotalPages());

        // Verifica que se llamó a los métodos correctos
        verify(iItemPersistencePort, times(1)).findAllPaged(page, size, sortBy, ascending);
        verify(iItemPersistencePort, times(1)).countItems();
    }

}
