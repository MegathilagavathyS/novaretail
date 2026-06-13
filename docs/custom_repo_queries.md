# Spring Boot Custom Queries with Spring Data JPA

## Objective

Learn how to search data from the database using custom repository methods without writing SQL queries.

This concept extends CRUD operations and introduces querying records based on specific fields.

---

# Prerequisites

Completed:

* REST API
* Controller Layer
* Service Layer
* Repository Layer
* MySQL Integration
* Validation
* DTO Pattern
* Global Exception Handling

---

# Problem Statement

Current APIs:

```text
GET    /users
GET    /users/{id}
POST   /users
PUT    /users/{id}
DELETE /users/{id}
```

These APIs only support CRUD operations.

Real-world applications require searching:

```text
GET /users/email/ravi@gmail.com
GET /users/name/Ravi
```

---

# What is a Custom Query?

Spring Data JPA can automatically generate SQL queries based on method names.

Example:

```java
findByEmail(String email)
```

Spring internally generates:

```sql
SELECT * FROM user
WHERE email = ?
```

No SQL code required.

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

# Repository Layer

## Purpose

Interact directly with the database.

Current:

```java
JpaRepository<User, Integer>
```

Provides:

```text
save()
findAll()
findById()
delete()
```

---

# Custom Query Methods

Added methods:

```java
Optional<User> findByEmail(String email);

List<User> findByName(String name);
```

---

# How Spring Converts Methods to SQL

## findByEmail

Method:

```java
findByEmail(String email)
```

Generated SQL:

```sql
SELECT *
FROM user
WHERE email = ?
```

---

## findByName

Method:

```java
findByName(String name)
```

Generated SQL:

```sql
SELECT *
FROM user
WHERE name = ?
```

---

# Service Layer

Purpose:

Business logic and DTO mapping.

---

## Search User By Email

Flow:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

Process:

```text
Email
   ↓
Repository Query
   ↓
User Entity
   ↓
UserResponseDTO
```

---

## Search Users By Name

Flow:

```text
Name
   ↓
Repository Query
   ↓
List<User>
   ↓
List<UserResponseDTO>
```

---

# Controller Layer

New Endpoints:

```text
GET /users/email/{email}

GET /users/name/{name}
```

These endpoints expose custom search functionality.

---

# Request Examples

## Search By Email

Request:

```http
GET http://localhost:8080/users/email/ravi@gmail.com
```

Response:

```json
{
  "id": 1,
  "name": "Ravi",
  "email": "ravi@gmail.com"
}
```

---

## Search By Name

Request:

```http
GET http://localhost:8080/users/name/Ravi
```

Response:

```json
[
  {
    "id": 1,
    "name": "Ravi",
    "email": "ravi@gmail.com"
  }
]
```

---

# Exception Handling

If user not found:

Request:

```http
GET /users/email/test@gmail.com
```

Response:

```json
{
  "message": "User not found with email: test@gmail.com"
}
```

Status:

```http
404 NOT FOUND
```

---

# Common Query Methods

## Exact Match

```java
findByName(String name)

findByEmail(String email)
```

---

## Multiple Conditions

```java
findByNameAndEmail(
    String name,
    String email
)
```

Generated SQL:

```sql
SELECT *
FROM user
WHERE name = ?
AND email = ?
```

---

## Contains

```java
findByNameContaining(String name)
```

Generated SQL:

```sql
SELECT *
FROM user
WHERE name LIKE '%value%'
```

Example:

```text
Ravi
```

Matches:

```text
Ravi
Ravikumar
Mr Ravi
```

---

## Starts With

```java
findByNameStartingWith(String name)
```

Example:

```text
Ra
```

Matches:

```text
Ravi
Ramesh
Rajesh
```

---

## Ends With

```java
findByEmailEndingWith(String domain)
```

Example:

```text
gmail.com
```

Matches:

```text
ravi@gmail.com
john@gmail.com
```

---

# Query Flow

```text
Client
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
Spring Data JPA
   ↓
Hibernate
   ↓
MySQL
```

---

# Advantages

## No SQL Needed

Method Name:

```java
findByEmail()
```

Spring generates SQL automatically.

---

## Faster Development

Less boilerplate code.

---

## Readable

Method names clearly describe purpose.

Example:

```java
findByEmail()

findByName()

findByNameContaining()
```

---

## Type Safe

Compile-time checking by Java.

---

# Concepts Learned

## Repository Query Methods

Methods that generate SQL automatically.

Examples:

```java
findByEmail()

findByName()

findByNameContaining()
```

---

## Optional

Used when result may or may not exist.

Example:

```java
Optional<User>
```

Avoids NullPointerException.

---

## DTO Mapping

Convert:

```text
Entity
   ↓
DTO
```

Before returning data to client.

---

## Custom Search APIs

Example:

```http
GET /users/email/{email}

GET /users/name/{name}
```

---

# Revision Checklist

* [ ] Added findByEmail()
* [ ] Added findByName()
* [ ] Updated Service Layer
* [ ] Updated Controller Layer
* [ ] Tested Search By Email
* [ ] Tested Search By Name
* [ ] Tested Not Found Scenario
* [ ] Verified DTO Response

---

# Final Architecture

```text
Client
   ↓
Controller
   ↓
DTO
   ↓
Service
   ↓
Repository
   ↓
Custom Query Methods
   ↓
Hibernate
   ↓
MySQL
```

---

# Next Topic

After Custom Queries:

```text
Pagination & Sorting
```

Example:

```http
GET /users?page=0&size=5

GET /users?page=0&size=5&sort=name
```

Why?

When thousands of records exist, fetching all users at once is inefficient. Pagination and Sorting are standard features in production APIs.
