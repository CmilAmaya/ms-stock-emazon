package com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.repository;

import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.entity.BrandEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBrandRepository extends JpaRepository<BrandEntity, Long> {
    Optional<BrandEntity> findByName(String name);
    void deleteByName(String name);
    Page<BrandEntity> findAll(Pageable pageable);
}
