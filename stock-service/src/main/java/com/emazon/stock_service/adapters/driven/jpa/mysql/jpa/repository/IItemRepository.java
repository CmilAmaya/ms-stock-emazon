package com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.repository;

import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IItemRepository extends JpaRepository<ItemEntity, Long> {
    Optional<ItemEntity> findByName(String name);
    void deleteByName(String name);

}
