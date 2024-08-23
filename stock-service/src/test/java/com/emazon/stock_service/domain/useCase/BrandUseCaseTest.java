package com.emazon.stock_service.domain.useCase;

import com.emazon.stock_service.domain.exception.BrandNotFoundException;
import com.emazon.stock_service.domain.exception.CategoryNotFoundException;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import com.emazon.stock_service.domain.exception.InvalidBrandNameException;
import com.emazon.stock_service.domain.utils.DomainConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrandUseCaseTest {

    private IBrandPersistencePort brandPersistencePort;
    private BrandUseCase brandUseCase;

    @BeforeEach
    public void setUp() {
        brandPersistencePort = mock(IBrandPersistencePort.class);
        brandUseCase = new BrandUseCase(brandPersistencePort);
    }

    @Test
    void testCreateBrandSuccessfully() {
        // Arrange
        Brand brand = new Brand(null, "Adidas", "Tienda de zapatos deportivos");

        when(brandPersistencePort.findByName(brand.getName())).thenReturn(Optional.empty());

        // Act
        brandUseCase.createBrand(brand);

        // Assert
        verify(brandPersistencePort).save(brand);
    }

    @Test
    void testCreateBrandWithExistingName() {
        // Arrange
        Brand brand = new Brand(null, "Adidas", "Tienda de zapatos deportivos");

        when(brandPersistencePort.findByName(brand.getName())).thenReturn(Optional.of(brand));

        // Act & Assert
        InvalidBrandNameException exception = assertThrows(
                InvalidBrandNameException.class,
                () -> brandUseCase.createBrand(brand)
        );

        assertEquals(DomainConstants.BRAND_ALREADY_EXISTS_MESSAGE, exception.getMessage());
        verify(brandPersistencePort, never()).save(brand);
    }


    @Test
    void testDeleteBrandSuccessfully() {
        // Arrange
        String brandName = "Adidas";
        Brand brand = new Brand(null, brandName, "Tienda de zapatos deportivos");

        when(brandPersistencePort.findByName(brandName)).thenReturn(Optional.of(brand));

        // Act
        brandUseCase.deleteBrand(brandName);

        // Assert
        verify(brandPersistencePort).delete(brandName);
    }

    @Test
    void testDeleteBrandNotFound() {
        // Arrange
        String brandName = "Adidas";

        when(brandPersistencePort.findByName(brandName)).thenReturn(Optional.empty());

        // Act & Assert
        BrandNotFoundException exception = assertThrows(
                BrandNotFoundException.class,
                () -> brandUseCase.deleteBrand(brandName)
        );

        assertEquals(DomainConstants.BRAND_NOT_FOUND, exception.getMessage());
        verify(brandPersistencePort, never()).delete(brandName);
    }

    @Test
    void testGetAllBrandsSuccessfullyAscending() {
        // Arrange
        int page = 0;
        int size = 5;
        boolean ascending = true;

        Brand brand1 = new Brand(null, "Adidas", "Brand A");
        Brand brand2 = new Brand(null, "Nike", "Brand B");

        Page<Brand> brandPage = new PageImpl<>(
                Arrays.asList(brand1, brand2),
                PageRequest.of(page, size, Sort.by("name").ascending()),
                2
        );

        when(brandPersistencePort.findAllBrands(PageRequest.of(page, size, Sort.by("name").ascending()))).thenReturn(brandPage);

        // Act
        Page<Brand> result = brandUseCase.getAllBrands(page, size, ascending);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("Adidas", result.getContent().get(0).getName());
        assertEquals("Nike", result.getContent().get(1).getName());
    }

    @Test
    void testGetAllBrandsSuccessfullyDescending() {
        // Arrange
        int page = 0;
        int size = 5;
        boolean ascending = false;

        Brand brand1 = new Brand(null, "Nike", "Brand B");
        Brand brand2 = new Brand(null, "Adidas", "Brand A");

        Page<Brand> brandPage = new PageImpl<>(
                Arrays.asList(brand1, brand2),
                PageRequest.of(page, size, Sort.by("name").descending()),
                2
        );

        when(brandPersistencePort.findAllBrands(PageRequest.of(page, size, Sort.by("name").descending()))).thenReturn(brandPage);

        // Act
        Page<Brand> result = brandUseCase.getAllBrands(page, size, ascending);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("Nike", result.getContent().get(0).getName());
        assertEquals("Adidas", result.getContent().get(1).getName());
    }

    @Test
    void testGetAllBrandsPagination() {
        // Arrange
        int page = 1;
        int size = 5;
        boolean ascending = true;

        Brand brand1 = new Brand(null, "Puma", "Brand C");
        Brand brand2 = new Brand(null, "Reebok", "Brand D");

        Page<Brand> brandPage = new PageImpl<>(
                Arrays.asList(brand1, brand2),
                PageRequest.of(page, size, Sort.by("name").ascending()),
                10
        );

        when(brandPersistencePort.findAllBrands(PageRequest.of(page, size, Sort.by("name").ascending()))).thenReturn(brandPage);

        // Act
        Page<Brand> result = brandUseCase.getAllBrands(page, size, ascending);

        // Assert
        assertNotNull(result);
        assertEquals(10, result.getTotalElements());
        assertEquals(2, result.getNumberOfElements());
        assertEquals(brand1, result.getContent().get(0));
        assertEquals(brand2, result.getContent().get(1));
    }
}
