package com.emazon.stock_service.domain.api;

import com.emazon.stock_service.domain.model.Brand;

public interface IBrandServicePort {
    void createBrand(Brand brand);
    void deleteBrand(String name);
    Brand getBrand(String name);
}
