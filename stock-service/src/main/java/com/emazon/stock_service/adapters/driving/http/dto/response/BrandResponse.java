package com.emazon.stock_service.adapters.driving.http.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BrandResponse {
    private  Long id;
    private  String name;
    private  String description;
}
