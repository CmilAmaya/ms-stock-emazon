package com.emazon.stock_service.application.handler;

import com.emazon.stock_service.application.dto.request.CategoryRequest;
import com.emazon.stock_service.application.dto.response.CategoryResponse;

import java.util.List;

public interface ICategoryHandler {
    void createCategory(CategoryRequest categoryRequest);


}
