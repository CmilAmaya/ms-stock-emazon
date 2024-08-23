package com.emazon.stock_service.domain.spi;

import com.emazon.stock_service.domain.model.Brand;
import java.util.Optional;
public interface IBrandPersistencePort {
    void save(Brand brand);
    void delete(String name);
    Optional<Brand> findByName(String name);
}
