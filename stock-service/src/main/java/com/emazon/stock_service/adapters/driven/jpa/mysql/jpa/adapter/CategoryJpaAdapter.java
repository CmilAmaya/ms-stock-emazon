package com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.adapter;

import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.repository.ICategoryRepository;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.mapper.ICategoryEntityMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
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
            throw new RuntimeException("Category already exists");
        }
        categoryRepository.save(categoryEntityMapper.toEntity(category));
    }
    @Override
    @Transactional
    public void delete(String name) {
        categoryRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.deleteByName(name);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name)
                .map(categoryEntityMapper::toDomainModel);
    }

    @Override
    public List<Category> findAllCategories(int page, int size, boolean ascending) {
        Sort sort = ascending ? Sort.by("name").ascending() : Sort.by("name").descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return categoryRepository.findAll(pageRequest)
                .map(categoryEntityMapper::toDomainModel)
                .getContent();
    }
}