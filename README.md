# Tax Calculator Application

## Overview
The Tax Calculator application is designed to calculate and manage tax-related information for employees. It provides APIs to add employees and calculate tax deductions.

## Features
- Add Employee API
- Tax Calculation API
- Swagger UI for API documentation
- Actuator endpoints for application monitoring

## Requirements
- Java 21 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher
- Spring Boot 3.4.0 or higher

## Setup Instructions

### Clone the Repository
```bash
git clone https://github.com/spkumarsunkari/taxcalculator.git
```

### Configuration
Update the following properties in `application.properties` as needed:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tax_calculator
spring.datasource.username=root
spring.datasource.password=root
```

### Database Setup
Install MySQL and create a database:
```sql
CREATE DATABASE tax_calculator;
```

### Build the Application
Use Maven to clean, compile, and package the application:
```bash
mvn clean install
```

### Run the Application
Run the Spring Boot application:
```bash
java -jar target/tax-calculator-0.0.1-SNAPSHOT.jar
```

## API Documentation

### Base URL
`http://localhost:8080/api/employees`

### 1. Add Employee
**Endpoint**
```
POST /api/employees
```
**Description**
Adds a new employee to the system.

**Request Body**
```json
{
  "employeeId": "E001",
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phoneNumbers": ["9876543210"],
  "doj": "2023-01-01",
  "salary": 50000
}
```

**Responses**
- **Success (201 Created)**
```json
{
  "employeeId": "E001",
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phoneNumbers": ["9876543210"],
  "doj": "2023-01-01",
  "salary": 50000
}
```

### 2. Get Tax Deductions
**Endpoint**
```
GET /api/employees/{employeeId}/tax-deductions
```
**Description**
Fetches the tax deduction details for an employee by their employee ID.

**Path Parameters**
| Parameter  | Type   | Description                     |
| ---------- | ------ | ------------------------------- |
| employeeId | String | The ID of the employee to fetch |

**Responses**
- **Success (200 OK)**
```json
{
  "employeeId": "E001",
  "firstName": "John",
  "lastName": "Doe",
  "yearlySalary": 600000,
  "taxAmount": 45000,
  "cessAmount": 0
}
```

### Error Codes
| HTTP Status Code | Error Message           | Description                              |
| ---------------- | ----------------------- | ---------------------------------------- |
| 201 Created      | -                       | Employee added successfully.             |
| 200 OK           | -                       | Request was successful.                  |
| 404 Not Found    | Employee not found      | Employee ID does not exist in the system.|
| 400 Bad Request  | Employee Already Exists | Employee ID exists in the system.        |

## Swagger UI
The application uses Swagger for API documentation. Once the application is running, access the Swagger UI at:
```
http://localhost:8080/swagger-ui.html
```

## Monitoring
Spring Actuator endpoints are enabled for application monitoring. You can access them at:
```
http://localhost:8080/actuator
```
