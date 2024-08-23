package com.emazon.stock_service.adapters.driving.http.controller;

import com.emazon.stock_service.adapters.driving.http.dto.request.BrandRequest;
import com.emazon.stock_service.adapters.driving.http.dto.response.BrandResponse;
import com.emazon.stock_service.adapters.driving.http.mapper.IBrandRequestMapper;
import com.emazon.stock_service.adapters.driving.http.mapper.IBrandResponseMapper;
import com.emazon.stock_service.domain.api.IBrandServicePort;
import com.emazon.stock_service.domain.model.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/brands")
public class BrandRestController {

    @Autowired
    private IBrandServicePort brandServicePort;

    @Autowired
    private IBrandRequestMapper brandRequestMapper;

    @Autowired
    private IBrandResponseMapper brandResponseMapper;

    @PostMapping
    public ResponseEntity<BrandResponse> createBrand(@RequestBody BrandRequest brandRequest){
        Brand brand = brandRequestMapper.toBrand(brandRequest);
        brandServicePort.createBrand(brand);
        BrandResponse response = brandResponseMapper.toBrandResponse(brand);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{name}")
    public ResponseEntity<BrandResponse> getBrand(@PathVariable("name") String name){
        Brand brand = brandServicePort.getBrand(name);
        BrandResponse response = brandResponseMapper.toBrandResponse(brand);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public ResponseEntity<List<BrandResponse>> getAllBrands(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sortOrder) {

        boolean ascending = "asc".equalsIgnoreCase(sortOrder);
        Page<Brand> brandPage = brandServicePort.getAllBrands(page, size, ascending);
        List<BrandResponse> brandResponseList = brandPage.getContent()
                .stream()
                .map(brandResponseMapper::toBrandResponse)
                .collect(Collectors.toList());

        return new ResponseEntity<>(brandResponseList, HttpStatus.OK);
    }



    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteBrand(@PathVariable("name") String name){
        brandServicePort.deleteBrand(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
