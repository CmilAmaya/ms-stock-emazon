package com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.repository;

import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(String name);
    void deleteByName(String name);
    Page<CategoryEntity> findAll(Pageable pageable);
}
