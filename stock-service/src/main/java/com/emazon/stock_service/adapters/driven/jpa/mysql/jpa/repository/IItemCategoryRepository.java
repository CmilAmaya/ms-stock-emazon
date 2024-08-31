package com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.repository;

import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.entity.ItemCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemCategoryRepository extends JpaRepository<ItemCategoryEntity, Long> {

}
