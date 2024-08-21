package com.emazon.stock_service.configuration.exceptionhandler;

import com.emazon.stock_service.domain.exception.EmptyFieldException;
import com.emazon.stock_service.domain.exception.InvalidCategoryNameException;
import com.emazon.stock_service.domain.exception.InvalidCategoryDescriptionException;
import com.emazon.stock_service.domain.exception.CategoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}