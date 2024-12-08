package com.taxcalculator.controller;

import com.taxcalculator.entity.Employee;
import com.taxcalculator.exceptions.EmployeeAlreadyExists;
import com.taxcalculator.exceptions.EmployeeNotFound;
import com.taxcalculator.response.TaxResponse;
import com.taxcalculator.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @PostMapping
    public ResponseEntity<Employee> addEmployee(Employee employee) throws EmployeeAlreadyExists {
        return new ResponseEntity<>(employeeService.addEmployee(employee), HttpStatus.CREATED);
    }

    @GetMapping("/{employeeId}/tax-deductions")
    public ResponseEntity<TaxResponse> getTaxDeductions(@PathVariable String employeeId) throws EmployeeNotFound {
        TaxResponse taxResponse = employeeService.calculateTaxForEmployee(employeeId);
        return new ResponseEntity<>(taxResponse, HttpStatus.OK);
    }
}
