package com.emazon.stock_service.domain.useCase;

import com.emazon.stock_service.domain.exception.BrandNotFoundException;
import com.emazon.stock_service.domain.exception.CategoryNotFoundException;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import com.emazon.stock_service.domain.exception.InvalidBrandNameException;
import com.emazon.stock_service.domain.utils.DomainConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
