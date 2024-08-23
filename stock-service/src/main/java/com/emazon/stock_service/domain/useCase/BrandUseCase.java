package com.emazon.stock_service.domain.useCase;

import com.emazon.stock_service.domain.api.IBrandServicePort;
import com.emazon.stock_service.domain.exception.BrandNotFoundException;
import com.emazon.stock_service.domain.exception.InvalidBrandNameException;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import com.emazon.stock_service.domain.utils.DomainConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class BrandUseCase implements IBrandServicePort {
    private final IBrandPersistencePort brandPersistencePort;
    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }
    @Override
    public void createBrand(Brand brand) {
        if(brandPersistencePort.findByName(brand.getName()).isPresent()) {
            throw new InvalidBrandNameException(DomainConstants.BRAND_ALREADY_EXISTS_MESSAGE);
        }
        brandPersistencePort.save(brand);
    }
    @Override
    public void deleteBrand(String name) {
        brandPersistencePort.findByName(name)
                .orElseThrow(() -> new BrandNotFoundException(DomainConstants.BRAND_NOT_FOUND));
        brandPersistencePort.delete(name);
    }
    @Override
    public Brand getBrand(String name) {
        return brandPersistencePort.findByName(name)
                .orElseThrow(() -> new InvalidBrandNameException(DomainConstants.BRAND_NOT_FOUND));
    }

    @Override
    public Page<Brand> getAllBrands(int page, int size, boolean ascending) {
        Sort sort = ascending ? Sort.by("name").ascending() : Sort.by("name").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return brandPersistencePort.findAllBrands(pageable);
    }
}
