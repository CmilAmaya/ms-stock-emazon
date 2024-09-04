package com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Articulo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UniqueID")
    private Long id;

    @Column(name = "nombre")
    private String name;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "cantidad")
    private Long quantity;

    @Column(name = "precio")
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_marca", referencedColumnName = "UniqueID", nullable = false)
    private BrandEntity brand;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ItemCategoryEntity> itemCategories;
}
