package com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.adapter;

import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.mapper.IBrandEntityMapper;
import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.repository.IBrandRepository;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BrandJpaAdapter implements IBrandPersistencePort {
    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;

    @Override
    @Transactional
    public void save(Brand brand) {
        if(brandRepository.findByName(brand.getName()).isPresent()) {
            throw new IllegalArgumentException("Brand already exists");
        }
        brandRepository.save(brandEntityMapper.toEntity(brand));
    }

    @Override
    @Transactional
    public void delete(String name) {
        brandRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));
        brandRepository.deleteByName(name);
    }

    @Override
    public Optional<Brand> findById(Long id) {
        return brandRepository.findById(id)
                .map(brandEntityMapper::toDomainModel);
    }

    @Override
    public Optional<Brand> findByName(String name) {
        return brandRepository.findByName(name)
                .map(brandEntityMapper::toDomainModel);
    }


    @Override
    public Page<Brand> findAllBrands(Pageable pageable) {
        return brandRepository.findAll(pageable)
                .map(brandEntityMapper::toDomainModel);
    }

}
