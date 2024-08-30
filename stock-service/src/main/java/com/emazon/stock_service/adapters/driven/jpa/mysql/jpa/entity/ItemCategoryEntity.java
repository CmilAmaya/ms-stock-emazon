package com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ArticuloCategoria")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UniqueID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", referencedColumnName = "UniqueID", nullable = false)
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_articulo", referencedColumnName = "UniqueID", nullable = false)
    private ItemEntity item;
}
