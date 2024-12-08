package com.taxcalculator.serviceimpl;

import com.taxcalculator.entity.Employee;
import com.taxcalculator.exceptions.EmployeeAlreadyExists;
import com.taxcalculator.exceptions.EmployeeNotFound;
import com.taxcalculator.repository.EmployeeRepository;
import com.taxcalculator.response.TaxResponse;
import com.taxcalculator.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Primary
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee addEmployee(Employee employee) throws EmployeeAlreadyExists {
        if (employeeRepository.existsById(employee.getEmployeeId())) {
            String message = String.format("Employee with given Employee Id: %s already exists", employee.getEmployeeId());
            LOG.info(message);
            throw new EmployeeAlreadyExists(message);
        }
        LOG.info("Employee {}", employee);
        return employeeRepository.save(employee);
    }

    public TaxResponse calculateTaxForEmployee(String employeeId) throws EmployeeNotFound {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isEmpty()) {
            LOG.info("Employee not found");
            throw new EmployeeNotFound("Employee not found");
        }
        Employee employee = optionalEmployee.get();
        double yearlySalary = employee.getSalary() * (12 - LocalDate.now().getMonthValue() + employee.getDoj().getMonthValue());
        double taxAmount = calculateTax(yearlySalary);
        double cessAmount = (yearlySalary > 2500000) ? (yearlySalary - 2500000) * 0.02 : 0;
        var taxResponse = new TaxResponse(employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(), yearlySalary, taxAmount, cessAmount);
        LOG.info("Employee Tax Response : {}", taxResponse);
        return taxResponse;
    }

    private double calculateTax(double salary) {
        if (salary <= 250000) return 0;
        if (salary <= 500000) return (salary - 250000) * 0.05;
        if (salary <= 1000000) return (250000 * 0.05) + (salary - 500000) * 0.10;
        double calculatedTax = (250000 * 0.05) + (500000 * 0.10) + (salary - 1000000) * 0.20;
        LOG.info("Calculated Tax : {}", calculatedTax);
        return calculatedTax;
    }
}
