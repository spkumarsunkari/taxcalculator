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
spring.datasource.url=jdbc:mysql://<host>:<port>/<database_name>
spring.datasource.username=<username>
spring.datasource.password=<password>
```

### Database Setup
Install MySQL and create a database:
```sql
CREATE DATABASE <database_name>;
```

### Build the Application
Use Maven to clean, compile, and package the application:
```bash
mvn clean install
```
### Build the Package
Use Maven to build the package
```bash
mvn build package
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
POST http://localhost:8080/api/employees
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
GET http://localhost:8080/api/employees/{employeeId}/tax-deductions
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

## Application Health Monitoring
Spring Actuator endpoints are enabled for application monitoring. You can access them at:
```
http://localhost:8080/actuator/health
```
```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "MySQL",
        "validationQuery": "isValid()"
      }
    },
    "diskSpace": {
      "status": "UP",
      "details": {
        "total": 264393191424,
        "free": 83266015232,
        "threshold": 10485760,
        "path": "C:\\Users\\Sai Prasanna Kumar\\Desktop\\taxcalculator\\.",
        "exists": true
      }
    },
    "ping": {
      "status": "UP"
    }
  }
}
```
### JUnit Testing
The application includes JUnit test cases to ensure the functionality works as expected. Tests are created for the main service classes and their logic.

## Dependencies
Make sure the following dependencies are available in your pom.xml for testing with JUnit 5:

```xml
Copy code
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.x.x</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit5</artifactId>
    <scope>test</scope>
</dependency>
```

## Running the Tests
You can run the test cases using Maven or your IDE:

### 1. Using Maven Command Line
``` bash
mvn test
```

### 2. Using an IDE
Most IDEs (IntelliJ, Eclipse, VSCode, etc.) support running test classes directly. Look for a "Run Tests" option in the test class.

## Coverage
The application includes JUnit test cases to validate:

* Adding an employee.
* Calculating employee tax deductions.
* Handling employee not found errors.
* Ensuring proper exception handling for edge cases.

