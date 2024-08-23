package com.emazon.stock_service.domain.api;

import com.emazon.stock_service.domain.model.Brand;
import org.springframework.data.domain.Page;

public interface IBrandServicePort {
    void createBrand(Brand brand);
    void deleteBrand(String name);
    Brand getBrand(String name);
    Page<Brand> getAllBrands(int page, int size, boolean ascending);
}
