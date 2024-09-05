package com.emazon.stock_service.adapters.driving.http.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ItemResponse {
    private  Long id;
    private  String name;
    private  String description;
    private  Long quantity;
    private  Long price;
    private  List<CategoryResponse> categories;
    private  BrandResponse brand;

}
