package com.taxcalculator.service;

import com.taxcalculator.entity.Employee;
import com.taxcalculator.exceptions.EmployeeAlreadyExists;
import com.taxcalculator.exceptions.EmployeeNotFound;
import com.taxcalculator.response.TaxResponse;


public interface EmployeeService {

    Employee addEmployee(Employee employee) throws EmployeeAlreadyExists;

    TaxResponse calculateTaxForEmployee(String employeeId) throws EmployeeNotFound;
}
