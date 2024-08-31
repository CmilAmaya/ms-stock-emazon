package com.emazon.stock_service.adapters.driving.http.controller;

import com.emazon.stock_service.adapters.driving.http.dto.request.BrandRequest;
import com.emazon.stock_service.adapters.driving.http.dto.response.BrandResponse;
import com.emazon.stock_service.adapters.driving.http.mapper.IBrandRequestMapper;
import com.emazon.stock_service.adapters.driving.http.mapper.IBrandResponseMapper;
import com.emazon.stock_service.domain.api.IBrandServicePort;
import com.emazon.stock_service.domain.model.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BrandRestControllerTest {

    @Mock
    private IBrandServicePort brandServicePort;

    @Mock
    private IBrandRequestMapper brandRequestMapper;

    @Mock
    private IBrandResponseMapper brandResponseMapper;

    @InjectMocks
    private BrandRestController brandRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBrand() {
        BrandRequest brandRequest = new BrandRequest("Test Brand", "Test Description");
        Brand brand = new Brand(1L, "Test Brand", "Test Description");
        BrandResponse brandResponse = new BrandResponse(1L, "Test Brand", "Test Description");

        when(brandRequestMapper.toBrand(any(BrandRequest.class))).thenReturn(brand);
        when(brandResponseMapper.toBrandResponse(any(Brand.class))).thenReturn(brandResponse);

        ResponseEntity<BrandResponse> response = brandRestController.createBrand(brandRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(brandResponse, response.getBody());
    }

}
