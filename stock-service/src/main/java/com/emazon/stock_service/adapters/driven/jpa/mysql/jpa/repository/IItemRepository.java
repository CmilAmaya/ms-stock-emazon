package com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.repository;

import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.entity.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IItemRepository extends JpaRepository<ItemEntity, Long> {
    Optional<ItemEntity> findByName(String name);
    void deleteByName(String name);
    @Query("SELECT i FROM ItemEntity i " +
            "JOIN i.brand b " +
            "LEFT JOIN i.itemCategories ic " +
            "LEFT JOIN ic.category c " +
            "ORDER BY CASE WHEN :sortBy = 'name' THEN i.name " +
            "               WHEN :sortBy = 'brand' THEN b.name " +
            "               ELSE i.name END")
    Page<ItemEntity> findAll(Pageable pageable, @Param("sortBy") String sortBy);
}
