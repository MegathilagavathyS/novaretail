# Spring Security + JWT Authentication

## Objective

Secure Spring Boot APIs using:

* Spring Security
* BCrypt Password Encoding
* JWT (JSON Web Token)
* Authentication

---

# Concepts Covered

## 1. Spring Security

Spring Security protects application endpoints from unauthorized access.

Without security:

```text
Client
   |
   v
API
```

With security:

```text
Client
   |
Authentication
   |
Authorization
   |
API
```

---

## 2. Authentication

Authentication verifies who the user is.

Example:

```json
{
  "email": "test@gmail.com",
  "password": "password123"
}
```

If credentials are correct:

```text
Authenticated
```

Otherwise:

```text
Access Denied
```

---

## 3. Authorization

Authorization determines what the authenticated user can access.

Example:

```text
ADMIN
 ├─ Create User
 ├─ Update User
 └─ Delete User

USER
 ├─ View User
 └─ View Profile
```

---

## 4. BCrypt Password Encoding

Passwords should never be stored in plain text.

Bad:

```text
password123
```

Good:

```text
$2a$10$o6fjFgVU4O7NBwagm4s...
```

Encoding Example:

```java
passwordEncoder.encode("password123");
```

Verification:

```java
passwordEncoder.matches(
    rawPassword,
    encodedPassword
);
```

---

## 5. JWT (JSON Web Token)

JWT is a signed token used to identify authenticated users.

Example:

```text
eyJhbGciOiJIUzI1NiJ9...
```

Contains:

* Header
* Payload
* Signature

---

# JWT Authentication Flow

```text
User Login
    |
    v
Spring Security
    |
Validate Credentials
    |
    v
Generate JWT Token
    |
    v
Client Stores Token
    |
    v
Client Sends Token
Authorization: Bearer <token>
    |
    v
JWT Filter Validates Token
    |
    v
Protected API Access
```

---

# Project Structure

```text
src/main/java/com/example/demo

├── config
│   └── SecurityConfig.java
│
├── security
│   └── JwtUtil.java
│
├── controller
│   └── AuthController.java
│
├── dto
│   ├── LoginRequest.java
│   └── LoginResponse.java
│
├── model
│   └── User.java
│
├── service
│   └── UserService.java
│
└── repository
    └── UserRepository.java
```

---

# Database Changes

User table now contains:

```sql
DESCRIBE user;
```

```text
+----------+--------------+
| Field    | Type         |
+----------+--------------+
| id       | int          |
| name     | varchar(255) |
| email    | varchar(255) |
| password | varchar(255) |
+----------+--------------+
```

---

# User Registration

API:

```http
POST /users
```

Request:

```json
{
  "name":"Test User",
  "email":"test@gmail.com",
  "password":"password123"
}
```

Password is encrypted before saving.

---

# User Login

API:

```http
POST /auth/login
```

Request:

```json
{
  "email":"test@gmail.com",
  "password":"password123"
}
```

Response:

```json
{
  "token":"eyJhbGciOiJIUzI1NiJ9..."
}
```

---

# Why Existing Users Cannot Login

Old users have:

```sql
password = NULL
```

Example:

```text
Ravi
Kumar
John
Arun
```

Only newly created users have BCrypt passwords.

Example:

```text
Test User
$2a$10$o6fjFgVU4O7NBwagm4s...
```

---

# SecurityConfig Purpose

Provides:

* PasswordEncoder Bean
* SecurityFilterChain
* Security Configuration

Example Responsibilities:

```text
Enable Security
Allow Requests
Disable CSRF (for REST APIs)
Register Password Encoder
```

---

# JwtUtil Purpose

Responsibilities:

```text
Generate Token
Validate Token
Extract User Email
Check Expiration
```

---

# Current Learning Progress

Completed:

✅ REST APIs

✅ Controller Layer

✅ Service Layer

✅ Repository Layer

✅ DTO Pattern

✅ Validation

✅ Global Exception Handling

✅ MySQL Integration

✅ JPA/Hibernate

✅ One-To-One Mapping

✅ One-To-Many Mapping

✅ Many-To-One Mapping

✅ Cascade Types

✅ Pagination & Sorting

✅ Spring Security Basics

✅ BCrypt Password Encoding

✅ JWT Token Generation

---

# Next Topics

1. JWT Filter
2. SecurityFilterChain Authorization Rules
3. UserDetailsService
4. Role-Based Authentication
5. ADMIN / USER Roles
6. Swagger/OpenAPI
7. Docker
8. Redis Caching
9. Kafka Messaging
10. Microservices

---

# Key Takeaways

* Never store plain passwords.
* Always use BCrypt.
* JWT is used for stateless authentication.
* Spring Security protects APIs.
* JWT tokens are generated after successful login.
* Clients must send JWT tokens for protected APIs.
* Authentication verifies identity.
* Authorization controls access.
