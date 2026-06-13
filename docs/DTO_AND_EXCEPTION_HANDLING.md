# Spring Boot DTO & Global Exception Handling

## Objective

Improve API design by:

1. Using DTOs (Data Transfer Objects)
2. Handling exceptions globally
3. Returning clean API responses
4. Avoiding direct exposure of database entities

---

# Why DTO?

## Before DTO

```text
Client
  ↓
Controller
  ↓
User Entity
  ↓
Service
  ↓
Repository
  ↓
MySQL
```

Problem:

The API exposes database entities directly.

Example:

```java
@Entity
public class User {
    private Integer id;
    private String name;
    private String email;
    private String password;
}
```

Response:

```json
{
  "id": 1,
  "name": "Ravi",
  "email": "ravi@gmail.com",
  "password": "123456"
}
```

Sensitive data may be exposed accidentally.

---

# After DTO

```text
Client
  ↓
Controller
  ↓
UserRequestDTO
  ↓
Service
  ↓
User Entity
  ↓
Repository
  ↓
UserResponseDTO
  ↓
Client
```

Benefits:

* Better API design
* Secure responses
* Separation of concerns
* Independent API models

---

# Project Structure

```text
com.example.demo
│
├── controller
│   └── UserController.java
│
├── service
│   └── UserService.java
│
├── repository
│   └── UserRepository.java
│
├── model
│   └── User.java
│
├── dto
│   ├── UserRequestDTO.java
│   └── UserResponseDTO.java
│
├── exception
│   ├── UserNotFoundException.java
│   └── GlobalExceptionHandler.java
│
└── DemoApplication.java
```

---

# DTO Layer

## UserRequestDTO

Purpose:

Receive data from client.

Example Request:

```json
{
  "name": "Ravi",
  "email": "ravi@gmail.com"
}
```

Contains:

```text
name
email
```

Validation annotations can be added here.

---

## UserResponseDTO

Purpose:

Return data to client.

Example Response:

```json
{
  "id": 1,
  "name": "Ravi",
  "email": "ravi@gmail.com"
}
```

Contains:

```text
id
name
email
```

---

# DTO Mapping

## Request Flow

```text
POST /users
      ↓
UserRequestDTO
      ↓
User Entity
      ↓
Repository
      ↓
MySQL
```

---

## Response Flow

```text
MySQL
  ↓
User Entity
  ↓
UserResponseDTO
  ↓
Client
```

---

# Validation

Purpose:

Reject invalid input before reaching Service Layer.

Example:

```json
{
  "name": "",
  "email": "abc"
}
```

Validation catches errors before saving data.

Common annotations:

```java
@NotBlank
@Email
@Size
@Min
@Max
```

---

# Exception Handling

## Why?

Without exception handling:

```text
Controller
  ↓
Service
  ↓
Exception
  ↓
Large Stack Trace
```

Client receives poor error information.

---

# Custom Exception

## UserNotFoundException

Purpose:

Throw when a user is not found.

Example:

```text
User ID = 999
```

Database Result:

```text
No Record Found
```

Exception:

```text
UserNotFoundException
```

---

# Global Exception Handler

## @RestControllerAdvice

Purpose:

Handle all application exceptions from one place.

Flow:

```text
Controller
  ↓
Service
  ↓
Exception
  ↓
GlobalExceptionHandler
  ↓
JSON Response
```

---

# Validation Exception Handling

Input:

```json
{
  "name": "",
  "email": "abc"
}
```

Response:

```json
{
  "name": "Name is required",
  "email": "Invalid email format"
}
```

Status:

```http
400 BAD REQUEST
```

---

# User Not Found Handling

Request:

```http
GET /users/999
```

Response:

```json
{
  "message": "User not found"
}
```

Status:

```http
404 NOT FOUND
```

---

# Updated Architecture

```text
Client
  ↓
Controller
  ↓
DTO
  ↓
Validation
  ↓
Service
  ↓
Repository
  ↓
MySQL
```

Error Flow:

```text
Controller
  ↓
Validation
  ↓
Exception
  ↓
GlobalExceptionHandler
  ↓
JSON Response
```

---

# Key Concepts Learned

## DTO

Data Transfer Object.

Used to transfer data between:

```text
Client ↔ Application
```

Examples:

```text
UserRequestDTO
UserResponseDTO
```

---

## Entity

Represents database table.

Example:

```text
User.java
```

Mapped to:

```text
user table
```

---

## Validation

Ensures only valid data enters the application.

Examples:

```text
@NotBlank
@Email
```

---

## Custom Exception

Represents business-specific errors.

Example:

```text
UserNotFoundException
```

---

## Global Exception Handler

Centralized location for handling errors.

Annotation:

```java
@RestControllerAdvice
```

---

# Benefits Achieved

✅ Clean API Responses

✅ Validation Before Database Operations

✅ Centralized Error Handling

✅ Better Security

✅ Separation of Entity and API Models

✅ Enterprise-Level Architecture

---

# Revision Checklist

* [ ] Created UserRequestDTO
* [ ] Created UserResponseDTO
* [ ] Updated Controller to use DTOs
* [ ] Updated Service to map DTO ↔ Entity
* [ ] Added Validation
* [ ] Created UserNotFoundException
* [ ] Created GlobalExceptionHandler
* [ ] Tested Validation Errors
* [ ] Tested User Not Found Errors
* [ ] Verified JSON Error Responses

---

# Final Architecture

```text
Client
  ↓
Controller
  ↓
DTO
  ↓
Validation
  ↓
Service
  ↓
Repository
  ↓
Hibernate
  ↓
MySQL
```

Exception Flow:

```text
Exception
  ↓
GlobalExceptionHandler
  ↓
HTTP Response
```

This architecture is commonly used in real Spring Boot applications and provides a strong foundation before learning Custom Queries, Pagination, Entity Relationships, and Spring Security.
