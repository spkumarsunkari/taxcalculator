package com.taxcalculator.service;

import com.taxcalculator.entity.Employee;
import com.taxcalculator.exceptions.EmployeeAlreadyExists;
import com.taxcalculator.exceptions.EmployeeNotFound;
import com.taxcalculator.repository.EmployeeRepository;
import com.taxcalculator.response.TaxResponse;
import com.taxcalculator.serviceimpl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class EmployeeServiceImplTest {

    private final EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
    private final EmployeeServiceImpl employeeService = new EmployeeServiceImpl(employeeRepository);

    @Test
    void testAddEmployee() throws EmployeeAlreadyExists {
        // Mock data
        Employee mockEmployee = new Employee();
        mockEmployee.setEmployeeId("E001");
        mockEmployee.setFirstName("John");
        mockEmployee.setLastName("Doe");

        // Mock repository behavior
        Mockito.when(employeeRepository.existsById("E001")).thenReturn(false);
        Mockito.when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);

        // Test addEmployee method
        Employee result = employeeService.addEmployee(mockEmployee);

        // Assertions
        assertEquals("E001", result.getEmployeeId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }

    @Test
    void testAddEmployeeThrowsException() {
        // Mock repository behavior
        Mockito.when(employeeRepository.existsById("E001")).thenReturn(true);

        // Assertions for exception
        EmployeeAlreadyExists exception = assertThrows(EmployeeAlreadyExists.class, () -> {
            Employee mockEmployee = new Employee();
            mockEmployee.setEmployeeId("E001");
            employeeService.addEmployee(mockEmployee);
        });

        assertEquals("Employee with given Employee Id: E001 already exists", exception.getMessage());
    }

    @Test
    void testCalculateTaxForEmployee() throws EmployeeNotFound {
        // Mock data
        Employee mockEmployee = new Employee();
        mockEmployee.setEmployeeId("E001");
        mockEmployee.setFirstName("John");
        mockEmployee.setLastName("Doe");
        mockEmployee.setSalary(500000);
        mockEmployee.setDoj(LocalDate.of(2024, 1, 1));

        // Mock repository behavior
        Mockito.when(employeeRepository.findById("E001")).thenReturn(Optional.of(mockEmployee));

        // Test calculateTaxForEmployee method
        TaxResponse result = employeeService.calculateTaxForEmployee("E001");

        // Assertions
        assertEquals("E001", result.getEmployeeId());
        assertEquals("John", result.getFirstName());
        assertTrue(result.getTaxAmount() > 0);
    }

    @Test
    void testCalculateTaxForEmployeeThrowsException() {
        // Mock repository behavior
        Mockito.when(employeeRepository.findById("E001")).thenReturn(Optional.empty());

        // Assertions for exception
        EmployeeNotFound exception = assertThrows(EmployeeNotFound.class, () ->
                employeeService.calculateTaxForEmployee("E001")
        );

        assertEquals("Employee not found", exception.getMessage());
    }
}