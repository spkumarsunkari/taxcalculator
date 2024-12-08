package com.taxcalculator.controller;

import com.taxcalculator.entity.Employee;
import com.taxcalculator.exceptions.EmployeeAlreadyExists;
import com.taxcalculator.exceptions.EmployeeNotFound;
import com.taxcalculator.response.TaxResponse;
import com.taxcalculator.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void testAddEmployee() throws Exception {
        // Mock Employee object
        Employee mockEmployee = new Employee();
        mockEmployee.setEmployeeId("E001");
        mockEmployee.setFirstName("John");
        mockEmployee.setLastName("Doe");
        mockEmployee.setSalary(50000);

        // Mock service behavior
        Mockito.when(employeeService.addEmployee(any(Employee.class))).thenReturn(mockEmployee);

        // Perform POST request
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"employeeId\": \"E001\", \"firstName\": \"John\", \"lastName\": \"Doe\", \"salary\": 50000}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employeeId").value("E001"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void testGetTaxDeductions() throws Exception {
        // Mock TaxResponse object
        TaxResponse mockTaxResponse = new TaxResponse("E001", "John", "Doe", 600000, 30000, 0);

        // Mock service behavior
        Mockito.when(employeeService.calculateTaxForEmployee(anyString())).thenReturn(mockTaxResponse);

        // Perform GET request
        mockMvc.perform(get("/api/employees/E001/tax-deductions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value("E001"))
                .andExpect(jsonPath("$.taxAmount").value(30000.0));
    }

    @Test
    void testAddEmployeeThrowsException() throws Exception {
        // Mock exception
        Mockito.when(employeeService.addEmployee(any(Employee.class))).thenThrow(new EmployeeAlreadyExists("Employee already exists"));

        // Perform POST request
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"employeeId\": \"E001\", \"firstName\": \"John\", \"lastName\": \"Doe\", \"salary\": 50000}"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("Employee already exists"));
    }

    @Test
    void testGetTaxDeductionsThrowsException() throws Exception {
        // Mock exception
        Mockito.when(employeeService.calculateTaxForEmployee(anyString())).thenThrow(new EmployeeNotFound("Employee not found"));

        // Perform GET request
        mockMvc.perform(get("/api/employees/E001/tax-deductions"))
                .andExpect(status().isNotFound()) // 404 Not Found
                .andExpect(content().string("Employee not found"));
    }
}
