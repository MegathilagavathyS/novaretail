# Spring Boot E-Commerce Backend Progress

## Technologies Used

* Java 17+
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate
* MySQL
* Spring Security
* JWT Authentication
* Maven

---

# Completed Modules

## 1. User Management

### Features

* Create User
* Get All Users
* Get User By Id
* Update User
* Delete User

### Concepts Learned

* REST APIs
* Controller Layer
* Service Layer
* Repository Layer
* Dependency Injection

---

## 2. MySQL Integration

### Features

* Connected Spring Boot to MySQL
* Automatic table generation

### Configuration

application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/spring_demo
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Concepts Learned

* JDBC
* DataSource
* Hibernate ORM
* JPA

---

## 3. DTO Layer

### Files

* UserRequestDTO
* UserResponseDTO

### Purpose

* Hide Entity Structure
* API Contract Separation
* Secure Data Exposure

### Concepts Learned

* Request DTO
* Response DTO
* Entity Mapping

---

## 4. Validation

### Annotations Used

```java
@NotBlank
@Email
@Size
```

### Concepts Learned

* Bean Validation
* Request Validation
* Input Sanitization

---

## 5. Global Exception Handling

### Files

* UserNotFoundException
* GlobalExceptionHandler

### Concepts Learned

* Custom Exceptions
* @ControllerAdvice
* @ExceptionHandler

---

## 6. Pagination & Sorting

### Example

```http
GET /users/page?page=0&size=5&sortBy=name
```

### Concepts Learned

* Pageable
* PageRequest
* Sort

---

## 7. One-To-Many Relationship

### User -> Address

```java
@OneToMany
```

### Concepts Learned

* Parent Child Relationship
* Bidirectional Mapping
* Foreign Keys

---

## 8. Many-To-One Relationship

### Address -> User

```java
@ManyToOne
```

### Concepts Learned

* JoinColumn
* Foreign Key Mapping

---

## 9. JSON Infinite Recursion Fix

### Annotations

```java
@JsonManagedReference
@JsonBackReference
```

### Concepts Learned

* Circular Reference Prevention
* Jackson Serialization

---

## 10. Cascade Types

### Used

```java
CascadeType.ALL
```

### Concepts Learned

* Persist
* Merge
* Remove
* Refresh
* Detach

---

## 11. One-To-One Relationship

### User -> Profile

```java
@OneToOne
```

### Concepts Learned

* Unique Foreign Keys
* User Profile Management

---

## 12. Many-To-Many Relationship

### Product <-> Category

Implemented using join relationships.

### Concepts Learned

* Join Tables
* Many-To-Many Mapping

---

## 13. JWT Authentication

### Features

* Register
* Login
* JWT Token Generation
* JWT Validation

### Endpoints

POST /auth/register

POST /auth/login

### Concepts Learned

* Authentication
* Authorization
* Token Based Security
* Password Encryption

---

## 14. Spring Security

### Features

* Protected APIs
* Stateless Sessions

### Concepts Learned

* SecurityFilterChain
* AuthenticationManager
* PasswordEncoder
* JWT Filter

---

## 15. Category Module

### APIs

POST /categories

GET /categories

GET /categories/{id}

DELETE /categories/{id}

### Concepts Learned

* Product Grouping
* Domain Modeling

---

## 16. Product Module

### APIs

POST /products

GET /products

GET /products/{id}

DELETE /products/{id}

GET /products/category/{id}

### Concepts Learned

* Product Catalog
* DTO Mapping
* Entity Relationships

---

## 17. Cart Module

### APIs

POST /cart/add

GET /cart/{userId}

### Concepts Learned

* Shopping Cart Design
* Cart Items
* Quantity Management
* User Product Relationship

---

# Current Database Tables

* user
* address
* profile
* category
* product
* cart
* cart_item

---

# Next Modules

## Order Management

* Place Order
* Order Items
* Order History
* Total Price Calculation

## Role Based Authorization

* ROLE_ADMIN
* ROLE_USER

## Swagger Documentation

* OpenAPI

## Docker

* Containerization

## React Frontend

* Login
* Product Listing
* Cart
* Checkout

## Deployment

* Render
* Railway
* AWS

---

# Current Project Level

Intermediate Spring Boot Developer

Project Type:
Mini E-Commerce Backend

Next Goal:
Production-Ready E-Commerce Application
