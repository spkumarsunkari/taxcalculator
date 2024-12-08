package com.taxcalculator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeAlreadyExists.class)
    public ResponseEntity<String> employeeAlreadyExists() {
        return new ResponseEntity<>("Employee already exists", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmployeeNotFound.class)
    public ResponseEntity<String> employeeNotFound() {
        return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
    }
}
