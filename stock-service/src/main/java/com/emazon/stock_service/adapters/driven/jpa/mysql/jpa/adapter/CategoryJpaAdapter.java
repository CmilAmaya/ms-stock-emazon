package com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.adapter;

import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.repository.ICategoryRepository;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.mapper.ICategoryEntityMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;


    @Override
    @Transactional
    public void save(Category category) {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new IllegalArgumentException("Category already exists");
        }
        categoryRepository.save(categoryEntityMapper.toEntity(category));
    }

    @Override
    @Transactional
    public void delete(String name) {
        categoryRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        categoryRepository.deleteByName(name);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name)
                .map(categoryEntityMapper::toDomainModel);
    }

    @Override
    public Page<Category> findAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryEntityMapper::toDomainModel);
    }
}