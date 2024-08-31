package com.emazon.stock_service.configuration.exceptionhandler;

import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.exception.BrandAlreadyExistsException;
import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.exception.CategoryAlreadyExistsException;
import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.exception.ItemAlreadyExistsException;
import com.emazon.stock_service.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<ExceptionResponse> handleEmptyFieldException(EmptyFieldException ex) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), "Empty field error");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCategoryNameException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidCategoryNameException(InvalidCategoryNameException ex) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), "Invalid category name error");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCategoryDescriptionException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidCategoryDescriptionException(InvalidCategoryDescriptionException ex) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), "Invalid category description error");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), "Category not found error");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCategoryNumberException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidCategoryNumberException(InvalidCategoryNumberException ex) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), "Invalid category number error");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BrandNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleBrandNotFoundException(BrandNotFoundException ex) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), "Brand not found error");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidBrandNameException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidBrandNameException(InvalidBrandNameException ex) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), "Invalid brand name error");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidBrandDescriptionException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidBrandDescriptionException(InvalidBrandDescriptionException ex) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), "Invalid brand description error");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleItemNotFoundException(ItemNotFoundException ex) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), "Item not found error");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidItemNameException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidItemNameException(InvalidItemNameException ex) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), "Invalid item name error");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Exception handler for Adapters
    @ExceptionHandler(BrandAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleBrandAlreadyExistsException(BrandAlreadyExistsException ex) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), "Brand already exists error");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException ex) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), "Category already exists error");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ItemAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleItemAlreadyExistsException(ItemAlreadyExistsException ex) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), "Item already exists error");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}