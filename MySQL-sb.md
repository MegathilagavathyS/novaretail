# Spring Boot + MySQL Integration Guide

## Objective

Connect a Spring Boot REST API application to MySQL using:

* Spring Data JPA
* Hibernate
* MySQL Connector

---

# Architecture

```text
Client (Postman)
      ↓
Controller
      ↓
Service
      ↓
Repository
      ↓
JPA / Hibernate
      ↓
MySQL Database
```

---

# Project Structure

```text
src/main/java/com/example/demo

├── DemoApplication.java
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
└── resources
    └── application.properties
```

---

# Step 1: Install MySQL

Verify MySQL installation:

```sql
SELECT VERSION();
```

Expected output:

```text
8.x.x
```

---

# Step 2: Create Database

Login to MySQL:

```sql
CREATE DATABASE spring_demo;
```

Verify:

```sql
SHOW DATABASES;
```

Expected:

```text
spring_demo
```

---

# Step 3: Add Dependencies

Update `pom.xml`.

Required dependencies:

```xml
spring-boot-starter-web
spring-boot-starter-data-jpa
mysql-connector-j
spring-boot-starter-test
```

Purpose:

| Dependency                   | Purpose         |
| ---------------------------- | --------------- |
| spring-boot-starter-web      | REST APIs       |
| spring-boot-starter-data-jpa | JPA & Hibernate |
| mysql-connector-j            | MySQL Driver    |
| spring-boot-starter-test     | Testing         |

---

# Step 4: Configure Database Connection

File:

```text
src/main/resources/application.properties
```

Configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/spring_demo
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.jpa.open-in-view=false
```

Explanation:

| Property            | Purpose                     |
| ------------------- | --------------------------- |
| datasource.url      | Database URL                |
| datasource.username | MySQL username              |
| datasource.password | MySQL password              |
| ddl-auto=update     | Auto-create/update tables   |
| show-sql=true       | Print SQL in console        |
| format_sql=true     | Pretty SQL formatting       |
| open-in-view=false  | Disable unnecessary warning |

---

# Step 5: Convert Model into Entity

File:

```text
model/User.java
```

Required annotations:

```java
@Entity
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
```

Purpose:

* `@Entity` → maps class to table
* `@Id` → primary key
* `@GeneratedValue` → auto-increment ID

---

# Step 6: Create Repository Layer

File:

```text
repository/UserRepository.java
```

Repository extends:

```java
JpaRepository<User, Integer>
```

Benefits:

* findAll()
* findById()
* save()
* delete()
* deleteById()
* count()

No SQL needs to be written manually.

---

# Step 7: Update Service Layer

Before:

```text
ArrayList<User>
```

After:

```text
UserRepository
```

Service Responsibilities:

* Get users
* Get user by ID
* Add user
* Update user
* Delete user

Service should communicate with Repository only.

---

# Step 8: Update Controller Layer

Controller Responsibilities:

### GET

```http
GET /users
```

Returns all users.

---

### GET BY ID

```http
GET /users/{id}
```

Returns one user.

---

### POST

```http
POST /users
```

Creates a user.

---

### PUT

```http
PUT /users/{id}
```

Updates a user.

---

### DELETE

```http
DELETE /users/{id}
```

Deletes a user.

---

# Step 9: Run Application

Run:

```bash
mvn spring-boot:run
```

or

Run:

```text
DemoApplication.java
```

Expected logs:

```text
Tomcat started on port 8080
```

```text
Found 1 JPA repository interface
```

```text
Database JDBC URL [jdbc:mysql://localhost:3306/spring_demo]
```

---

# Step 10: Test APIs

## Create User

Request:

```http
POST http://localhost:8080/users
```

Body:

```json
{
  "name": "Ravi",
  "email": "ravi@gmail.com"
}
```

---

## Get Users

Request:

```http
GET http://localhost:8080/users
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

# Step 11: Verify Data in MySQL

Select database:

```sql
USE spring_demo;
```

View users:

```sql
SELECT * FROM user;
```

Example:

```text
+----+-----------------+-------+
| id | email           | name  |
+----+-----------------+-------+
|  1 | ravi@gmail.com  | Ravi  |
|  2 | kumar@gmail.com | Kumar |
+----+-----------------+-------+
```

---

# Key Concepts Learned

## Entity

Maps Java class to database table.

```text
User.java → user table
```

---

## Repository

Provides database operations.

```text
save()
findAll()
findById()
delete()
```

---

## Service

Contains business logic.

```text
Controller → Service → Repository
```

---

## JPA

Java Persistence API.

Used to interact with databases using Java objects.

---

## Hibernate

JPA implementation used by Spring Boot.

Converts:

```java
userRepository.save(user);
```

into SQL:

```sql
INSERT INTO user ...
```

---

# Request Flow

```text
POST /users
        ↓
UserController
        ↓
UserService
        ↓
UserRepository
        ↓
Hibernate
        ↓
MySQL
```

---

# Revision Checklist

* [ ] MySQL installed
* [ ] Database created
* [ ] JPA dependency added
* [ ] MySQL connector added
* [ ] application.properties configured
* [ ] Entity created
* [ ] Repository created
* [ ] Service updated
* [ ] Controller updated
* [ ] Application starts successfully
* [ ] POST API works
* [ ] GET API works
* [ ] Data visible in MySQL

---

# Final Outcome

Successfully built a Spring Boot CRUD REST API connected to MySQL using:

* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL
* REST APIs
* Layered Architecture (Controller → Service → Repository)
