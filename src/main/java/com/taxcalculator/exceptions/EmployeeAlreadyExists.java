package com.taxcalculator.exceptions;


public class EmployeeAlreadyExists extends Exception {
    public EmployeeAlreadyExists(String message) {
        super(message);
    }
}
