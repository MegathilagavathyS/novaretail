# Spring Boot REST API Learning Journey

## Project Overview

This project was built step-by-step to learn Spring Boot backend development using:

* Spring Boot
* Spring Web
* Spring Data JPA
* MySQL
* Hibernate
* REST APIs

---

# Folder Structure

```text
src
└── main
    └── java
        └── com.example.demo

            ├── controller
            │   └── UserController.java

            ├── service
            │   └── UserService.java

            ├── repository
            │   ├── UserRepository.java
            │   ├── AddressRepository.java
            │   ├── ProfileRepository.java
            │   └── RoleRepository.java

            ├── model
            │   ├── User.java
            │   ├── Address.java
            │   ├── Profile.java
            │   └── Role.java

            ├── dto
            │   ├── UserRequestDTO.java
            │   ├── UserResponseDTO.java
            │   └── AddressDTO.java

            ├── exception
            │   ├── UserNotFoundException.java
            │   └── GlobalExceptionHandler.java

            └── DemoApplication.java

resources
└── application.properties
```

---

# Architecture

```text
Client
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
Database (MySQL)
```

---

# Topics Learned

---

## 1. REST API Basics

Created CRUD APIs.

### GET

```http
GET /users
```

Fetch all users.

### POST

```http
POST /users
```

Create user.

### PUT

```http
PUT /users/{id}
```

Update user.

### DELETE

```http
DELETE /users/{id}
```

Delete user.

---

## 2. Service Layer

Purpose:

```text
Controller
   ↓
Business Logic
   ↓
Repository
```

Responsibilities:

* Validation
* Business rules
* Data transformation
* Calling repositories

---

## 3. Repository Layer

Used:

```java
JpaRepository<User, Integer>
```

Benefits:

* save()
* findById()
* findAll()
* delete()

No SQL required for basic operations.

---

## 4. MySQL Integration

Added dependencies:

* spring-boot-starter-data-jpa
* mysql-connector-j

Configured:

```properties
spring.datasource.url
spring.datasource.username
spring.datasource.password
```

Hibernate automatically created tables.

---

## 5. DTO Pattern

### Problem

Never expose Entity directly.

### Solution

Request DTO

```text
Client → DTO → Service
```

Response DTO

```text
Entity → DTO → Client
```

Files:

```text
UserRequestDTO
UserResponseDTO
```

Benefits:

* Security
* Cleaner API contracts
* Flexibility

---

## 6. Validation

Used:

```java
@NotBlank
@Email
```

Example:

```java
@NotBlank(message="Name is required")
private String name;
```

Controller:

```java
@Valid @RequestBody UserRequestDTO dto
```

Benefits:

* Input validation
* Automatic error handling

---

## 7. Global Exception Handling

Created:

```java
UserNotFoundException
```

Handled using:

```java
@RestControllerAdvice
```

Benefits:

```text
Centralized Error Handling
```

Example:

```json
{
  "message": "User not found"
}
```

---

## 8. Custom Queries

Repository methods:

```java
findByEmail(String email)
```

```java
findByName(String name)
```

Spring automatically generates SQL.

---

## 9. Pagination

Purpose:

Avoid loading huge datasets.

Example:

```http
GET /users/paginated?page=0&size=5
```

Implementation:

```java
PageRequest.of(page,size)
```

Benefits:

* Better performance
* Scalable APIs

---

## 10. Sorting

Example:

```http
GET /users/paginated?page=0&size=5&sortBy=name
```

Implementation:

```java
Sort.by("name")
```

---

# JPA Relationships

---

## 11. One-To-Many Relationship

### User → Addresses

One User can have many Addresses.

```text
User
 ├── Address 1
 ├── Address 2
 └── Address 3
```

Implementation:

```java
@OneToMany
```

---

## 12. Many-To-One Relationship

### Address → User

Many addresses belong to one user.

```java
@ManyToOne
```

Database:

```text
address
   ↓
user_id
```

---

## 13. Foreign Key

Database:

```text
address.user_id
```

Purpose:

Link address to user.

---

## 14. Bidirectional Mapping

Navigation from both sides.

```text
User → Address

Address → User
```

Annotations:

```java
@OneToMany
@ManyToOne
```

---

## 15. JSON Infinite Recursion Fix

Problem:

```text
User
 ↓
Address
 ↓
User
 ↓
Address
```

Infinite loop.

Solution:

```java
@JsonManagedReference
```

```java
@JsonBackReference
```

---

## 16. Lazy Loading

Used:

```java
fetch = FetchType.LAZY
```

Benefits:

* Better performance
* Loads child entities only when needed

---

## 17. Cascade Types

Purpose:

Automatically propagate operations.

```java
cascade = CascadeType.ALL
```

Includes:

```text
PERSIST
MERGE
REMOVE
REFRESH
DETACH
```

Example:

```text
Save User
      ↓
Save Addresses
```

Automatically.

---

## 18. Orphan Removal

Used:

```java
orphanRemoval = true
```

Meaning:

```text
Remove Address from User
           ↓
Delete Address from DB
```

---

## 19. One-To-One Relationship

### User ↔ Profile

```text
User
  ↔
Profile
```

One user has one profile.

Annotations:

```java
@OneToOne
@JoinColumn
```

Database:

```text
profile.user_id
```

---

## 20. Many-To-Many Relationship

### User ↔ Role

```text
User
  ↔
Role
```

Examples:

```text
Ravi → ADMIN
Ravi → USER
```

Role can belong to many users.

Implementation:

```java
@ManyToMany
@JoinTable
```

Join Table:

```text
user_role
```

Structure:

```text
user_id
role_id
```

---

# Hibernate Concepts Learned

---

## Entity

```java
@Entity
```

Represents database table.

---

## Primary Key

```java
@Id
@GeneratedValue
```

Auto-generated IDs.

---

## Table Mapping

```java
@Table(name="user")
```

Maps class to table.

---

## Column Mapping

```java
@Column
```

Maps fields to columns.

---

# APIs Implemented

## User APIs

```http
GET    /users
GET    /users/{id}
POST   /users
PUT    /users/{id}
DELETE /users/{id}
```

---

## Search APIs

```http
GET /users/email/{email}
GET /users/name/{name}
```

---

## Pagination

```http
GET /users/paginated
```

---

## Address APIs

```http
POST /users/{userId}/addresses
GET  /users/{userId}/addresses
```

---

## Profile APIs

```http
POST /users/{userId}/profile
GET  /users/{userId}/profile
```

---

## Role APIs

```http
POST /users/roles
POST /users/{userId}/roles/{roleId}
```

---

# Database Tables

```text
user
address
profile
role
user_role
```

---

# Learning Progress

Completed:

```text
✅ REST APIs
✅ Controller Layer
✅ Service Layer
✅ Repository Layer
✅ MySQL Integration
✅ Spring Data JPA
✅ DTO Pattern
✅ Validation
✅ Exception Handling
✅ Pagination
✅ Sorting
✅ Custom Queries
✅ One-To-Many
✅ Many-To-One
✅ One-To-One
✅ Many-To-Many
✅ Cascade Types
✅ Lazy Loading
✅ Foreign Keys
✅ Hibernate Mappings
```

---

# Recommended Next Topics

1. Spring Security
2. JWT Authentication
3. Role Based Authorization
4. Audit Fields (createdAt, updatedAt)
5. Lombok
6. Swagger / OpenAPI
7. Unit Testing (JUnit + Mockito)
8. Docker
9. Spring Profiles
10. Microservices

The next topic should be:

```text
Spring Security + JWT Authentication
```

because it builds directly on the User ↔ Role relationship already implemented.
