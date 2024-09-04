package com.emazon.stock_service.adapters.driving.http.controller;

import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.mapper.IItemEntityMapper;
import com.emazon.stock_service.adapters.driving.http.dto.request.ItemRequest;
import com.emazon.stock_service.adapters.driving.http.dto.response.ItemResponse;
import com.emazon.stock_service.adapters.driving.http.dto.response.PaginatedResponse;
import com.emazon.stock_service.adapters.driving.http.mapper.IItemRequestMapper;
import com.emazon.stock_service.adapters.driving.http.mapper.IItemResponseMapper;
import com.emazon.stock_service.domain.api.IItemServicePort;
import com.emazon.stock_service.domain.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
public class ItemRestController {

    @Autowired
    private IItemServicePort itemServicePort;

    @Autowired
    private IItemRequestMapper itemRequestMapper;

    @Autowired
    private IItemResponseMapper itemResponseMapper;

    @Autowired
    private IItemEntityMapper itemEntityMapper;

    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@RequestBody ItemRequest itemRequest) {
        Item item = itemRequestMapper.toItem(itemRequest);
        itemServicePort.createItem(item);
        ItemResponse itemResponse = itemResponseMapper.toItemResponse(item);
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ItemResponse> getItem(@PathVariable("name") String name ) {
        Item item = itemServicePort.getItem(name);
        ItemResponse response = itemResponseMapper.toItemResponse(item);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<PaginatedResponse<ItemResponse>> getAllItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {

        PaginatedResponse<ItemResponse> paginatedItems = itemServicePort.getAllItems(page, size, sortBy, ascending);

        List<ItemResponse> itemResponses = paginatedItems.getItems();

        PaginatedResponse<ItemResponse> response = new PaginatedResponse<>(
                itemResponses, paginatedItems.getTotalItems(), paginatedItems.getTotalPages(), page, size);


        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteItem(@PathVariable("name") String name) {
        itemServicePort.deleteItem(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
